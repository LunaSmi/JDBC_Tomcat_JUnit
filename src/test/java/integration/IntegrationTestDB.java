package integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utils.ConnectionManager;

import java.sql.SQLException;

public abstract  class IntegrationTestDB {

    private static final String CLEAN_SQL = """
        DELETE FROM categories;
        DELETE FROM actors;
        """;
    private static final String DROP_SQL = """
        DROP TABLE IF EXISTS movie_actor;
        DROP TABLE IF EXISTS actors;
        DROP TABLE IF EXISTS movies;
        DROP TABLE IF EXISTS categories;
        """;
    private static final String CREATE_CATEGORIES_SQL = """
        CREATE TABLE IF NOT EXISTS categories
        (
            category_id serial PRIMARY KEY,
            name varchar(100) NOT NULL
        );
        """;
    private static final String CREATE_ACTORS_SQL = """
        CREATE TABLE IF NOT EXISTS actors
        (
            actor_id serial PRIMARY KEY,
            full_name varchar(200) NOT NULL
        );
        """;
    private static final String CREATE_MOVIES_SQL = """
        CREATE TABLE IF NOT EXISTS movies
        (
            movie_id serial PRIMARY KEY,
            title VARCHAR(200) NOT NULL,
            description VARCHAR(200),
            fk_category_id integer,
            foreign key (fk_category_id) references categories(category_id)
        );
        CREATE TABLE IF NOT EXISTS movie_actor
        (
            movie_id integer,
            foreign key (movie_id) references movies(movie_id),
            actor_id integer,
            foreign key (actor_id) references actors(actor_id),
            CONSTRAINT movie_actor_pkey PRIMARY KEY(movie_id,actor_id)
        );
        """;
    private static final String INSERT_CATEGORY_SQL = """
        INSERT INTO categories (name)
        VALUES
        ('комедия'),
        ('драма')
        """;
    private static final String INSERT_ACTOR_SQL = """
        INSERT INTO actors (full_name)
        VALUES
        ('Франсуа Клюзе'),
        ('Омар Си')
        """;
    private static final String INSERT_MOVIE_SQL = """
        INSERT INTO movies (title,description,fk_category_id)              
        VALUES
        ('1+1','Аристократ на коляске нанимает в сиделки бывшего заключенного.', 1),
        ('Шоколад','В угоду цирковой публике белый клоун измывается над черным.',2)
        """;
    private static final String INSERT_MOVIE_ACTOR_SQL = """
        INSERT INTO movie_actor
        VALUES
        (1,1),
        (1,2),
        (2,2)
        """;


    @BeforeAll
    static void prepareDatabase() throws SQLException {
        try (var connection = ConnectionManager.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_CATEGORIES_SQL);
            statement.execute(CREATE_ACTORS_SQL);
            statement.execute(CREATE_MOVIES_SQL);
            statement.execute(INSERT_CATEGORY_SQL);
            statement.execute(INSERT_ACTOR_SQL);
            statement.execute(INSERT_MOVIE_SQL);
            statement.execute(INSERT_MOVIE_ACTOR_SQL);
        }
    }


    @AfterAll
    static void deleteFromDatabase() throws SQLException {
         try (var connection = ConnectionManager.getConnection();
             var statement = connection.createStatement()) {
             statement.execute(DROP_SQL);
         }
    }



}



