package dao;

import entities.Category;
import integration.IntegrationTestDB;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDaoIT extends IntegrationTestDB {

    private final CategoryDao categoryDao = CategoryDao.getInstance();


    @Test
    @Order(1)
    void saveCategory() throws SQLException {
        var cat = new Category("test1");

        var actualResult1 = categoryDao.save(cat) ;

        Assertions.assertTrue(actualResult1);
    }

    @Test
    @Order(2)
    void findAll() throws SQLException {

        List<Category> actualResult = categoryDao.findAll();

        Assertions.assertEquals(actualResult.size(), 3);
        Assertions.assertEquals(actualResult.get(0).getName(), "комедия");
    }

    @Test
    @Order(3)
    void findById() throws SQLException {
        var cat = new Category("комедия");

        Optional<Category> actualResult = categoryDao.findById(1);

        Assertions.assertEquals(true, actualResult.isPresent());
        Assertions.assertEquals(actualResult.get(), cat);
    }


    @Test
    @Order(4)
    void updateCategory() throws SQLException {
        var cat3 = new Category(3,"test33");
        categoryDao.save(cat3);
        cat3 = new Category(3,"test3");

        var actualResult = categoryDao.update(cat3);

        Assertions.assertTrue(actualResult);
    }

    @Test
    @Order(5)
    void deleteCategory() throws SQLException {
        var actualResult = categoryDao.delete(3);

        Assertions.assertTrue(actualResult);
    }



}
