import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class RoomDAOTest {

    private RoomDAO roomDAO;

    @BeforeEach
    public void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        roomDAO = RoomDAO.getInstance();
    }

    @Test
    public void insertTest() {

        Room room = new Room("RoomTest");
        roomDAO.insert(room);
        assertNotNull(roomDAO.getById("RoomTest"));
        //reset previous state of the database
        roomDAO.delete(room);
    }

    @Test
    public void deleteTest() {

        Room room = new Room("RoomTest");
        roomDAO.insert(room);
        roomDAO.delete(room);
        assertNull(roomDAO.getById("RoomTest"));
    }

    @Test
    public void getByIdTest() {

        Room room = new Room("RoomTest");
        roomDAO.insert(room);
        assertEquals(roomDAO.getById("RoomTest").getName(), room.getName());
        //reset previous state of the database
        roomDAO.delete(room);
    }
}
