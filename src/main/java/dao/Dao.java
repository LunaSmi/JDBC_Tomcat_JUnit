package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T,K>{
    Optional<T> findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean save(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(K id) throws SQLException;
}
