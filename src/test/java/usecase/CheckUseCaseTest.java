package usecase;

import edu.unifi.model.entities.*;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.model.orm.dao.TypeOfCourseDAO;
import edu.unifi.model.util.security.CurrentSession;
import edu.unifi.model.util.security.Roles;
import edu.unifi.view.Home;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.JOptionPaneFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.timing.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;

public class CheckUseCaseTest {
    private FrameFixture window;
    private Table table;
    private Dish dish;

    @BeforeEach
    public void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        User user = new User();
        user.setRole(Roles.ADMIN);
        if (CurrentSession.getInstance().isLogged()) CurrentSession.getInstance().logout();
        CurrentSession.getInstance().login(user);
        TypeOfCourse typeOfCourse = new TypeOfCourse();
        typeOfCourse.setName("TestTypeOfCourse");
        TypeOfCourseDAO.getInstance().insert(typeOfCourse);

        Room room = new Room();
        room.setName("TestRoom");
        RoomDAO.getInstance().insert(room);

        dish = new Dish();
        dish.setName("TestDish");
        dish.setDescription("TestDescription");
        dish.setPrice(1000);
        dish.setTypeOfCourse(typeOfCourse);
        DishDAO.getInstance().insert(dish);

        table = new Table();
        table.setName("TestTable");
        table.setNOfSeats(4);
        table.setState(edu.unifi.model.entities.TableState.FREE);
        table.setRoom(room);
        TableDAO.getInstance().insert(table);

        Home frame = GuiActionRunner.execute(() -> new Home("Test Home"));
        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }

    // Test for UC-1 "Add order"
    @Test
    public void addOrder() throws InterruptedException {
        window.button(table.getName()).click();
        FrameFixture tableUpdateToolFrame = findFrame("Table Update Tool").withTimeout(1000).using(window.robot());

        tableUpdateToolFrame.button("New Check").click();
        GenericTypeMatcher<JOptionPane> matcher = new GenericTypeMatcher<>(JOptionPane.class) {
            protected boolean isMatching(JOptionPane optionPane) {
                return "New check created successfully".equals(optionPane.getMessage());
            }
        };
        JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane(matcher).using(window.robot());

        // Waiting for DAO to update table and for observer to notify the changes
        pause(new Condition("Wait JOptionPane to show") {
            public boolean test() {
                return optionPane.isEnabled();
            }
        }, timeout());
        optionPane.okButton().click();

        tableUpdateToolFrame.button("Add").click();

        FrameFixture dishViewFrame = findFrame("Order Creation Tool").withTimeout(10000).using(window.robot());
        dishViewFrame.spinner("Quantity" + dish.getId()).increment(1);
        dishViewFrame.button("Add" + dish.getId()).click();
        dishViewFrame.close();

        tableUpdateToolFrame.textBox("TotalField").requireText("20");
        tableUpdateToolFrame.label("TotalLabel" + dish.getId()).requireText("20.0");
        tableUpdateToolFrame.label("QuantityLabel" + dish.getId()).requireText("2");
        tableUpdateToolFrame.label("DishNameLabel" + dish.getId()).requireText(dish.getName());
        tableUpdateToolFrame.close();
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
        DatabaseAccess.terminate();
        CurrentSession.getInstance().logout();
    }
}
