import edu.unifi.model.entities.*;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.*;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDAOTest {

    private OrderDAO orderDAO;
    private DishDAO dishDAO;
    private CheckDAO checkDAO;
    private TypeOfCourseDAO typeOfCourseDAO;


    @BeforeEach
    public void setUp() {

        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        orderDAO = OrderDAO.getInstance();
        dishDAO = DishDAO.getInstance();
        checkDAO = CheckDAO.getInstance();
        typeOfCourseDAO = TypeOfCourseDAO.getInstance();
    }

    /*@Test
    public void insertTest() {

        Dish dish = new Dish();
        TypeOfCourse typeOfCourse = new TypeOfCourse();
        typeOfCourse.setName("courseTest");
        dish.setDescription("DescriptionTest");
        dish.setName("DishTest");
        dish.setPicture("PictureTest");
        dish.setPrice(1);
        dish.setTypeOfCourse(typeOfCourse);

        OrderId orderId = new OrderId();
    } */

}
