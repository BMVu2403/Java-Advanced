package PTIT_CNTT1_IT203B_Session10.Exercise01.src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    private static final String DB_URL = "jdbc:mysql://192.168.1.10:3306/Hospital_DB";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "med123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD);
    }
}
