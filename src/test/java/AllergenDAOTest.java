import edu.unifi.model.entities.*;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AllergenDAOTest {

    private static AllergenDAO allergenDAO;

    @BeforeAll
    public static void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        allergenDAO = AllergenDAO.getInstance();
    }

    @AfterEach
    public void cleanUp() {
        Session session = DatabaseAccess.open();
        Query q1 = session.createQuery("delete from Allergen");
        q1.executeUpdate();
        DatabaseAccess.close(session);
    }

    @Test
    public void insertTest() {

        Allergen allergen = new Allergen();
        allergen.setName("TestAllergen");
        allergenDAO.insert(allergen);
        assertNotNull(allergenDAO.getById("TestAllergen"));
    }

    @Test
    public void deleteTest() {

        Allergen allergen = new Allergen();
        allergen.setName("TestAllergen");
        allergenDAO.insert(allergen);
        allergenDAO.delete(allergen);
        assertNull(allergenDAO.getById("TestAllergen"));
    }

    @Test
    public void updateTest() {

        Allergen allergen = new Allergen();
        allergen.setName("TestAllergen");
        allergenDAO.insert(allergen);
        allergen.setName("TestAllergenUpdated");
        allergenDAO.update(allergen);
        assertEquals(allergenDAO.getById(allergen.getName()).getName(), allergen.getName());
    }

    @Test
    public void getByIdTest() {

        Allergen allergen = new Allergen();
        allergen.setName("TestAllergen");
        allergenDAO.insert(allergen);
        assertEquals(allergenDAO.getById("TestAllergen").getName(), allergen.getName());
    }
}
