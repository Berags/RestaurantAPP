package usecase;

import edu.unifi.model.entities.*;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.*;
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

import java.awt.print.PrinterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    public void addOrder() {
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

        tableUpdateToolFrame.label("TotalLabel" + dish.getId()).requireText("20.0");
        tableUpdateToolFrame.label("QuantityLabel" + dish.getId()).requireText("2");
        tableUpdateToolFrame.label("DishNameLabel" + dish.getId()).requireText(dish.getName());
        tableUpdateToolFrame.close();
    }

    @Test
    public void printCheck() {
        Check check = new Check();
        check.setTable(table);
        check.setIssueDate(LocalDateTime.now());
        OrderId oid = new OrderId(check, dish);
        Order o = new Order(oid);
        o.setQuantity(2);

        CheckDAO.getInstance().insert(check);
        OrderDAO.getInstance().insert(o);
        window.button(table.getName()).click();
        FrameFixture tableUpdateToolFrame = findFrame("Table Update Tool").withTimeout(1000).using(window.robot());

        assertDoesNotThrow(() -> tableUpdateToolFrame.button("Print Receipt").click());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
        DatabaseAccess.terminate();
        CurrentSession.getInstance().logout();
    }
}
