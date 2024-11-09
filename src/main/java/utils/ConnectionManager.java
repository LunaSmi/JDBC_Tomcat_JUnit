package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConnectionManager {

    private ConnectionManager(){}
    static{
        loadDriver();
    }

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(PropertiesUtil.getProperty("db.url"),
                    PropertiesUtil.getProperty("db.username"), PropertiesUtil.getProperty("db.password"));
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try{
            Class.forName( PropertiesUtil.getProperty("db.driver"));
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

}
