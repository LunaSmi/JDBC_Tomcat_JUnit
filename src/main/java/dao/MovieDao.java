package dao;

import entities.Movie;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDao implements Dao<Movie, Integer> {
    private MovieDao(){}
    private static final MovieDao INSTANCE = new MovieDao();

    public static MovieDao getInstance() {
        return INSTANCE;
    }


    private static final String SAVE_SQL = "INSERT INTO movies (title,description,fk_category_id) VALUES (?,?,?)";
    private static final String GET_ALL_SQL = "SELECT * FROM movies";
    private static final String GET_BD_ID_SQL = "SELECT * FROM movies WHERE movie_id = ?";
    private static final String UPDATE_BY_ID_SQL = """
        UPDATE movies SET title = ? , description = ? , fk_category_id = ? 
        WHERE movie_id = ?
        """;
    private static final String DELETE_BY_ID_SQL = """
        DELETE FROM movie_actor WHERE movie_id = ?;
        DELETE FROM movies WHERE movie_id = ?;
        """;



    @Override
    public Optional<Movie> findById(Integer id) throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(GET_BD_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(buildMovie(resultSet))
                    : Optional.empty();

        }
    }

    @Override
    public List<Movie> findAll() throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(GET_ALL_SQL,ResultSet. TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.last();
            int count = resultSet.getRow();
            List<Movie> movies  = new ArrayList<>(count);

            resultSet.beforeFirst();
            while (resultSet.next()) {
                movies.add(buildMovie(resultSet));
            }

            return movies;
        }
    }

    @Override
    public boolean save(Movie movie) throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDescription());
            preparedStatement.setInt(3,movie.getCategoryId());

            var result = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int movie_id = -1;
            if (rs.next()) {
                movie_id = rs.getInt("movie_id");
            }

            var temp = save_movie_actor(connection,movie_id,movie.getActorIDS());

            return (result  > 0) && temp ;
        }
    }

    @Override
    public boolean update(Movie movie) throws SQLException {

        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)){

            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDescription());
            preparedStatement.setInt(3, movie.getCategoryId());
            preparedStatement.setInt(4, movie.getId());

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




    private Movie buildMovie(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setTitle(resultSet.getString("title"));
        movie.setDescription(resultSet.getString("description"));
        movie.setCategoryId(resultSet.getInt("fk_category_id"));
        return movie;
    }


    private boolean save_movie_actor(Connection connection, int movie_id, int[] actor_ids ) throws SQLException {
        try(var preparedStatement = connection.prepareStatement("INSERT INTO movie_actor (movie_id,actor_id) VALUES (?,?)")){

            var result =0;
            preparedStatement.setInt(1, movie_id);
            for(int actor_id : actor_ids){
                preparedStatement.setInt(2, actor_id);
                result+=preparedStatement.executeUpdate();
            }
            return result > 0;
        }
    }


}
