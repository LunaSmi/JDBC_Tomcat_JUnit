package dao;

import entities.Actor;
import utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ActorDao implements Dao<Actor, Integer>  {
    private ActorDao(){}
    private static final ActorDao INSTANCE = new ActorDao();

    public static ActorDao getInstance() {
        return INSTANCE;
    }

    private static final String GET_ALL_SQL = "SELECT * FROM actors";
    private static final String GET_BD_ID_SQL = "SELECT * FROM actors WHERE actor_id = ?";
    private static final String SAVE_SQL = "INSERT INTO actors (full_name) VALUES (?)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE actors SET full_name = ? WHERE actor_id = ?";
    private static final String DELETE_BY_ID_SQL = """
        DELETE FROM movie_actor WHERE actor_id = ?;
        DELETE FROM actors WHERE actor_id = ?
        """;

    @Override
    public Optional<Actor> findById(Integer id) throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(GET_BD_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(buildActor(resultSet))
                    : Optional.empty();

        }
    }

    @Override
    public List<Actor> findAll() throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(GET_ALL_SQL,ResultSet. TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.last();
            int count = resultSet.getRow();
            List<Actor> actors  = new ArrayList<>(count);

            resultSet.beforeFirst();
            while (resultSet.next()) {
                actors.add(buildActor(resultSet));
            }

            return actors;
        }
    }

    @Override
    public boolean save(Actor actor) throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(SAVE_SQL)){

            preparedStatement.setString(1, actor.getFullName());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Actor actor) throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)){

            preparedStatement.setString(1, actor.getFullName());
            preparedStatement.setInt(2, actor.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }



    private Actor buildActor(ResultSet rs) throws SQLException {
        Actor actor = new Actor();
        actor.setId(rs.getInt("actor_id"));
        actor.setFullName(rs.getString("full_name"));
        return actor;
    }
}
