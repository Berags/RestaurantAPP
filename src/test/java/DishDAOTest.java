import edu.unifi.model.entities.*;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.model.orm.dao.TypeOfCourseDAO;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class DishDAOTest {

    private DishDAO dishDAO;
    private TypeOfCourseDAO typeOfCourseDAO;

    @BeforeEach
    public void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        dishDAO = DishDAO.getInstance();
        typeOfCourseDAO = TypeOfCourseDAO.getInstance();
    }

    @Test
    public void insertTest() {

        Dish dish = new Dish();
        TypeOfCourse typeOfCourse = new TypeOfCourse();

        typeOfCourse.setName("courseTest");
        dish.setDescription("DescriptionTest");
        dish.setName("DishTest");
        dish.setPicture("PictureTest");
        dish.setPrice(1);
        dish.setTypeOfCourse(typeOfCourse);

        typeOfCourseDAO.insert(typeOfCourse);
        dishDAO.insert(dish);

        assertNotNull(dishDAO.getById(dish.getId()));

        //reset previous state of the database
        dishDAO.delete(dish);
        typeOfCourseDAO.delete(typeOfCourse);
    }

    @Test
    public void deleteTest(){

        Dish dish = new Dish();
        TypeOfCourse typeOfCourse = new TypeOfCourse();

        typeOfCourse.setName("courseTest");
        dish.setDescription("DescriptionTest");
        dish.setName("DishTest");
        dish.setPicture("PictureTest");
        dish.setPrice(1);
        dish.setTypeOfCourse(typeOfCourse);

        typeOfCourseDAO.insert(typeOfCourse);
        dishDAO.insert(dish);
        dishDAO.delete(dish);
        typeOfCourseDAO.delete(typeOfCourse);

        assertNull(dishDAO.getById(dish.getId()));

    }

    @Test
    public void updateTest() {

        Dish dish = new Dish();
        TypeOfCourse typeOfCourse1 = new TypeOfCourse();
        TypeOfCourse typeOfCourse2 = new TypeOfCourse();

        typeOfCourse1.setName("CourseTest1");
        typeOfCourse2.setName("CourseTest2");
        dish.setDescription("DescriptionTest1");
        dish.setName("DishTest1");
        dish.setPicture("PictureTest1");
        dish.setPrice(1);
        dish.setTypeOfCourse(typeOfCourse1);

        typeOfCourseDAO.insert(typeOfCourse1);
        typeOfCourseDAO.insert(typeOfCourse2);
        dishDAO.insert(dish);

        dish.setDescription("DescriptionTest2");
        dish.setName("DishTest2");
        dish.setPicture("PictureTest2");
        dish.setPrice(2);
        dish.setTypeOfCourse(typeOfCourse2);
        dishDAO.update(dish);

        assertEquals(dishDAO.getById(dish.getId()).getDescription(), "DescriptionTest2");
        assertEquals(dishDAO.getById(dish.getId()).getName(), "DishTest2");
        assertEquals(dishDAO.getById(dish.getId()).getPicture(), "PictureTest2");
        assertEquals(dishDAO.getById(dish.getId()).getPrice(), 2);
        assertEquals(dishDAO.getById(dish.getId()).getTypeOfCourse().getName(), typeOfCourse2.getName());

        //reset the previous state of the database
        dishDAO.delete(dish);
        typeOfCourseDAO.delete(typeOfCourse1);
        typeOfCourseDAO.delete(typeOfCourse2);

    }

    @Test
    public void getById() {

        Dish dish = new Dish();
        TypeOfCourse typeOfCourse = new TypeOfCourse();

        typeOfCourse.setName("courseTest");
        dish.setDescription("DescriptionTest");
        dish.setName("DishTest");
        dish.setPicture("PictureTest");
        dish.setPrice(1);
        dish.setTypeOfCourse(typeOfCourse);

        typeOfCourseDAO.insert(typeOfCourse);
        dishDAO.insert(dish);

        assertEquals(dishDAO.getById(dish.getId()).getId(), dish.getId());
        assertEquals(dishDAO.getById(dish.getId()).getDescription(), dish.getDescription());
        assertEquals(dishDAO.getById(dish.getId()).getName(), dish.getName());
        assertEquals(dishDAO.getById(dish.getId()).getPicture(), dish.getPicture());
        assertEquals(dishDAO.getById(dish.getId()).getPrice(), dish.getPrice());
        assertEquals(dishDAO.getById(dish.getId()).getTypeOfCourse().getName(), dish.getTypeOfCourse().getName());

        //reset the previous state of the database
        dishDAO.delete(dish);
        typeOfCourseDAO.delete(typeOfCourse);

    }
}
