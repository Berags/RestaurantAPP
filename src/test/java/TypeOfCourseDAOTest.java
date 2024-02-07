import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.model.orm.dao.TypeOfCourseDAO;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TypeOfCourseDAOTest {

    private TypeOfCourseDAO typeOfCourseDAO;

    @BeforeEach
    public void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        typeOfCourseDAO = TypeOfCourseDAO.getInstance();
    }

    @Test
    public void insertTest() {

        TypeOfCourse typeOfCourse = new TypeOfCourse();
        typeOfCourse.setName("CourseTest");
        typeOfCourseDAO.insert(typeOfCourse);
        assertNotNull(typeOfCourseDAO.getById("CourseTest"));
        //reset the previous state of the database
        typeOfCourseDAO.delete(typeOfCourse);
    }

    @Test
    public void deleteTest() {

        TypeOfCourse typeOfCourse = new TypeOfCourse();
        typeOfCourse.setName("CourseTest");
        typeOfCourseDAO.insert(typeOfCourse);
        typeOfCourseDAO.delete(typeOfCourse);
        assertNull(typeOfCourseDAO.getById("CourseTest"));
    }

    @Test
    public void getByIdTest() {

        TypeOfCourse typeOfCourse = new TypeOfCourse();
        typeOfCourse.setName("CourseTest");
        typeOfCourseDAO.insert(typeOfCourse);
        assertEquals(typeOfCourseDAO.getById("CourseTest").getName(), typeOfCourse.getName());
        //reset the previous state of the database
        typeOfCourseDAO.delete(typeOfCourse);
    }

    @Test
    public void deleteByNameTest() {

        TypeOfCourse typeOfCourse = new TypeOfCourse();
        typeOfCourse.setName("CourseTest");
        typeOfCourseDAO.insert(typeOfCourse);
        typeOfCourseDAO.deleteByName("CourseTest");
        assertNull(typeOfCourseDAO.getById("CourseTest"));
    }



}
