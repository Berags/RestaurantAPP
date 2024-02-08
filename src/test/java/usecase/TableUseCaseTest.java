package usecase;

import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.model.util.security.CurrentSession;
import edu.unifi.model.util.security.Roles;
import edu.unifi.view.Home;
import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.finder.JOptionPaneFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.timing.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;

public class TableUseCaseTest {
    private FrameFixture window;
    private Table table;

    @BeforeEach
    public void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        User user = new User();
        user.setRole(Roles.ADMIN);
        if (CurrentSession.getInstance().isLogged()) CurrentSession.getInstance().logout();
        CurrentSession.getInstance().login(user);

        table = new Table();
        table.setName("TestTable");
        table.setNOfSeats(4);
        table.setState(edu.unifi.model.entities.TableState.FREE);
        Room room = new Room();
        room.setName("TestRoom");

        Room room2 = new Room();
        room2.setName("TestRoom2");
        RoomDAO.getInstance().insert(room);
        RoomDAO.getInstance().insert(room2);

        table.setRoom(room);
        TableDAO.getInstance().insert(table);
        Home frame = GuiActionRunner.execute(() -> new Home("Test Home"));
        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }

    // Test for UC-3 "Edit table information"
    @Test
    public void editTableInformation() {
        window.button(table.getName()).click();
        FrameFixture tableUpdateToolFrame = findFrame("Table Update Tool").withTimeout(1000).using(window.robot());

        // Checking if table information is correctly displayed
        tableUpdateToolFrame.textBox("nameTextField").requireText(table.getName());
        tableUpdateToolFrame.spinner("nOfSeatsSpinner").requireValue(table.getNOfSeats());
        tableUpdateToolFrame.comboBox("roomComboBox").requireSelection(table.getRoom().getName());
        tableUpdateToolFrame.comboBox("stateComboBox").requireSelection(table.getState().toString());

        // Updating information and saving
        tableUpdateToolFrame.textBox("nameTextField").deleteText().enterText("UpdatedTable");
        tableUpdateToolFrame.spinner("nOfSeatsSpinner").increment(2);
        tableUpdateToolFrame.comboBox("roomComboBox").selectItem("TestRoom2");
        tableUpdateToolFrame.comboBox("stateComboBox").selectItem(TableState.DINING.toString());
        tableUpdateToolFrame.button("createButton").click();
        GenericTypeMatcher<JOptionPane> matcher = new GenericTypeMatcher<JOptionPane>(JOptionPane.class) {
            protected boolean isMatching(JOptionPane optionPane) {
                return "UpdatedTable successfully updated!".equals(optionPane.getMessage());
            }
        };
        JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane(matcher).using(window.robot());

        // Waiting for DAO to update table and for observer to notify the changes
        pause(new Condition("Wait JOptionPane to show") {
            public boolean test() {
                return optionPane.isEnabled();
            }

        }, timeout(10000));
        optionPane.okButton().click();

        tableUpdateToolFrame.close();

        // Checking if table information is correctly updated
        window.tabbedPane().selectTab("TestRoom2");
        window.button("UpdatedTable").background().requireEqualTo(TableState.DINING.getColor());
        Assertions.assertTrue(window.button("UpdatedTable").text().contains("Seats: " + (table.getNOfSeats() + 2) + "<br>" + TableState.DINING));

        window.button("UpdatedTable").click();
        tableUpdateToolFrame = findFrame("Table Update Tool").withTimeout(1000).using(window.robot());
        tableUpdateToolFrame.textBox("nameTextField").requireText("UpdatedTable");
        tableUpdateToolFrame.spinner("nOfSeatsSpinner").requireValue(table.getNOfSeats() + 2);
        tableUpdateToolFrame.comboBox("roomComboBox").requireSelection("TestRoom2");
        tableUpdateToolFrame.comboBox("stateComboBox").requireSelection(TableState.DINING.toString());
    }

    // Test for UC-5 "Remove table"
    @Test
    public void removeTable() {
        window.menuItem("RemoveTable").click();
        FrameFixture tableDeletionTool = findFrame("Table Deletion Tool").withTimeout(1000).using(window.robot());
        tableDeletionTool.comboBox("RoomComboBox").selectItem(table.getRoom().getName());
        tableDeletionTool.comboBox("TableComboBox").selectItem(table.getName());
        tableDeletionTool.button("RemoveButton").click();
        GenericTypeMatcher<JOptionPane> matcher = new GenericTypeMatcher<JOptionPane>(JOptionPane.class) {
            protected boolean isMatching(JOptionPane optionPane) {
                return "Table successfully deleted!".equals(optionPane.getMessage());
            }
        };
        JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane(matcher).using(window.robot());

        // Waiting for DAO to update table and for observer to notify the changes
        pause(new Condition("Wait JOptionPane to show") {
            public boolean test() {
                return optionPane.isEnabled();
            }

        }, timeout(10000));
        optionPane.okButton().click();
        ComponentFinder finder = BasicComponentFinder.finderWithNewAwtHierarchy();
        Assertions.assertThrows(ComponentLookupException.class, () -> finder.findByName(table.getName()));
        Assertions.assertNull(TableDAO.getInstance().getById(table.getId()));
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
        DatabaseAccess.terminate();
        CurrentSession.getInstance().logout();
    }
}
