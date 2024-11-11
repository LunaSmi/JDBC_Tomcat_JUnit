package dao;

import entities.Actor;
import integration.IntegrationTestDB;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActorDaoIT extends IntegrationTestDB {

    private final ActorDao actorDao = ActorDao.getInstance();

    @Test
    @Order(1)
    void saveActor() throws SQLException {
        var act1 = new Actor("actor_1");
        var act2 = new Actor("actor_2");

        var actualResult1 = actorDao.save(act1) ;
        var actualResult2 = actorDao.save(act2) ;

        Assertions.assertTrue(actualResult1);
        Assertions.assertTrue(actualResult2);
    }

    @Test
    @Order(2)
    void findAll() throws SQLException {

        List<Actor> actualResult = actorDao.findAll();

        Assertions.assertEquals(actualResult.size(), 4);
        Assertions.assertEquals(actualResult.get(3).getFullName(), "actor_2");
    }

    @Test
    @Order(3)
    void findById() throws SQLException {

        Optional<Actor> actualResult = actorDao.findById(3);

        Assertions.assertEquals(true, actualResult.isPresent());
        Assertions.assertEquals(actualResult.get().getFullName(), "actor_1");
    }

    @Test
    @Order(4)
    void updateActor() throws SQLException {
        var act3 = new Actor(3,"actor33");
        actorDao.save(act3);
        act3 = new Actor(3,"actor_3");

        var actualResult = actorDao.update(act3);

        Assertions.assertTrue(actualResult);
    }

    @Test
    @Order(5)
    void deleteActor() throws SQLException {
        var actualResult = actorDao.delete(2);

        Assertions.assertTrue(actualResult);
    }

}
