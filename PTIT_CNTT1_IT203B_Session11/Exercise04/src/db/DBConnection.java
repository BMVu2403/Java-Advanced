package PTIT_CNTT1_IT203B_Session11.Exercise04.src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/benhvien",
                "root",
                "123456");
    }
}
