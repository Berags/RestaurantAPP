package usecase;

import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.DishDAO;
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

/**
 * This class is responsible for testing the use case of editing a dish in the system.
 * It uses the AssertJ Swing library to simulate user interactions with the GUI.
 */
public class DishUseCaseTest {
    private FrameFixture window;
    private Dish dish;

    /**
     * This method is called before each test.
     * It sets up the environment for the test, including creating a dish and opening the application window.
     */
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

        dish = new Dish();
        dish.setName("TestDish");
        dish.setDescription("TestDescription");
        dish.setPrice(10);
        dish.setTypeOfCourse(typeOfCourse);
        DishDAO.getInstance().insert(dish);

        Home frame = GuiActionRunner.execute(() -> new Home("Test Home"));
        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }

    /**
     * This method tests the use case of editing a dish.
     * It simulates the user interactions required to edit a dish and checks that the dish is updated correctly.
     */
    @Test
    public void editDish() {
        window.menuItem("EditDish").click();
        FrameFixture dishList = findFrame("Dish View").withTimeout(1000).using(window.robot());
        dishList.button("EditDish" + dish.getId()).click();
        FrameFixture dishUpdateTool = findFrame("Dish Update Tool").withTimeout(1000).using(window.robot());

        dishUpdateTool.textBox("NameField").requireText(dish.getName());
        String str = Integer.toString(dish.getPrice());
        str = new StringBuilder(str).insert(str.length() - 2, ".").toString();
        dishUpdateTool.textBox("PriceTextField").requireText(str);
        dishUpdateTool.comboBox("TypeComboBox").requireSelection(dish.getTypeOfCourse().getName());
        dishUpdateTool.textBox("DescriptionTextArea").requireText(dish.getDescription());

        dishUpdateTool.textBox("NameField").deleteText().enterText("NewTestDish");
        dishUpdateTool.textBox("PriceTextField").deleteText().enterText("20.00");
        dishUpdateTool.textBox("DescriptionTextArea").deleteText().enterText("NewTestDescription");
        dishUpdateTool.comboBox("TypeComboBox").selectItem(0);
        dishUpdateTool.button("CreateDish").click();
        GenericTypeMatcher<JOptionPane> matcher = new GenericTypeMatcher<>(JOptionPane.class) {
            protected boolean isMatching(JOptionPane optionPane) {
                return "NewTestDish updated successfully".equals(optionPane.getMessage());
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
        dishList.close();

        window.menuItem("EditDish").click();
        dishList = findFrame("Dish View").withTimeout(1000).using(window.robot());
        dishList.button("EditDish" + dish.getId()).click();
        dishUpdateTool = findFrame("Dish Update Tool").withTimeout(1000).using(window.robot());
        dishUpdateTool.textBox("NameField").requireText("NewTestDish");
        dishUpdateTool.textBox("PriceTextField").requireText("20.00");
        dishUpdateTool.comboBox("TypeComboBox").requireSelection(0);
        dishUpdateTool.textBox("DescriptionTextArea").requireText("NewTestDescription");
        dishUpdateTool.close();
    }

    /**
     * This method is called after each test.
     * It cleans up the environment, including closing the application window and logging out the current session.
     */
    @AfterEach
    public void tearDown() {
        window.cleanUp();
        DatabaseAccess.terminate();
        CurrentSession.getInstance().logout();
    }
}