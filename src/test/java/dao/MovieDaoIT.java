package dao;

import entities.Actor;
import entities.Movie;
import integration.IntegrationTestDB;
import org.junit.jupiter.api.*;

import java.sql.SQLException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieDaoIT extends IntegrationTestDB {

    private final MovieDao movieDao = MovieDao.getInstance();

    @Test
    @Order(1)
    void saveMovie() throws SQLException {
        var mov1 = new Movie(1,"test1","description1",2,new int[]{1,2});
        var mov2 = new Movie(2,"test2","description2",1,new int[]{1});

        var actualResult1 = movieDao.save(mov1);
        var actualResult2 = movieDao.save(mov2);

        Assertions.assertTrue(actualResult1);
        Assertions.assertTrue(actualResult2);
    }

    @Test
    @Order(2)
    void findAll() throws SQLException {
        var actualResult = movieDao.findAll();

        Assertions.assertEquals(actualResult.size(), 4);
        Assertions.assertEquals(actualResult.get(2).getTitle(), "test1");
        Assertions.assertEquals(actualResult.get(2).getDescription(), "description1");

    }

    @Test
    @Order(3)
    void findById() throws SQLException {
        var actualResult = movieDao.findById(3);

        Assertions.assertEquals(true, actualResult.isPresent());
        Assertions.assertEquals(actualResult.get().getTitle(), "test1");

    }

    @Test
    @Order(4)
    void updateMovie() throws SQLException {
        var mov3 = new Movie(3,"test33","description33",1,new int[]{1,2});
        movieDao.save(mov3);
        mov3 = new Movie(3,"test3","description3",1,new int[]{1,2});

        var actualResult = movieDao.update(mov3);

        Assertions.assertTrue(actualResult);
    }

    @Test
    @Order(5)
    void deleteMovie() throws SQLException {
        var actualResult = movieDao.delete(3);
        Assertions.assertTrue(actualResult);
    }

}
