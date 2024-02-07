import edu.unifi.model.entities.Ingredient;
import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.IngredientDAO;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {

    private IngredientDAO ingredientDAO;

    @BeforeEach
    public void setUp() {
        DatabaseAccess.setHibernateConfigFileToTest();
        DatabaseAccess.initiate();
        ingredientDAO = IngredientDAO.getInstance();
    }

    @Test
    public void insertTest() {

        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        ingredient.setDescription("TestDescription");
        ingredientDAO.insert(ingredient);
        assertNotNull(ingredientDAO.getById("TestIngredient"));
        //reset the previous state of the database
        ingredientDAO.delete(ingredient);
    }

    @Test
    public void deleteTest() {

        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        ingredient.setDescription("TestDescription");
        ingredientDAO.insert(ingredient);
        ingredientDAO.delete(ingredient);
        assertNull(ingredientDAO.getById("TestIngredient"));
    }

    @Test
    public void updateTest() {

        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        ingredient.setDescription("TestDescription");
        ingredientDAO.insert(ingredient);
        ingredient.setName("TestIngredientUpdated");
        ingredient.setDescription("TestDescriptionUpdated");
        ingredientDAO.update(ingredient);
        assertEquals(ingredientDAO.getById(ingredient.getName()).getName(), ingredient.getName());
        assertEquals(ingredientDAO.getById(ingredient.getName()).getDescription(), ingredient.getDescription());
        //reset the previous state of the database
        ingredientDAO.delete(ingredient);
    }

    @Test
    public void getByIdTest() {

        Ingredient ingredient = new Ingredient();
        ingredient.setName("TestIngredient");
        ingredient.setDescription("TestDescription");
        ingredientDAO.insert(ingredient);
        assertEquals(ingredientDAO.getById(ingredient.getName()).getName(), ingredient.getName());
        ingredientDAO.delete(ingredient);
    }
}
