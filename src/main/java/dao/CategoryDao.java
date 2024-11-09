package dao;

import entities.Category;
import utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao implements Dao<Category,Integer> {
    private CategoryDao(){}
    private static final CategoryDao INSTANCE = new CategoryDao();

    public static CategoryDao getInstance() {
        return INSTANCE;
    }
    private static final String GET_ALL_SQL = "SELECT * FROM categories";
    private static final String GET_BY_ID_SQL = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SAVE_SQL = "INSERT INTO categories (name) VALUES (?)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE categories SET name = ? WHERE category_id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM categories WHERE category_id = ?";

    @Override
    public Optional<Category> findById(Integer id) throws SQLException {

        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(buildCategory(resultSet))
                    : Optional.empty();

        }

    }

    @Override
    public List<Category> findAll() throws SQLException {

        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(GET_ALL_SQL,ResultSet. TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.last();
            int count = resultSet.getRow();
            List<Category> categories  = new ArrayList<>(count);

            resultSet.beforeFirst();
            while (resultSet.next()) {
                categories.add(buildCategory(resultSet));
            }

            return categories;
        }
    }

    @Override
    public boolean save(Category category) throws SQLException {

        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(SAVE_SQL)){

            preparedStatement.setString(1, category.getName());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Category category) throws SQLException {
        try(var connection = ConnectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)){

            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }


    private Category buildCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        return category;
    }



}
