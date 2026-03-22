package PTIT_CNTT1_IT203B_Session10.Exercise01.src.repository;

import java.sql.Connection;
import java.sql.SQLException;

import PTIT_CNTT1_IT203B_Session10.Exercise01.src.db.DBContext;

public class PatientRepository {

    public void testConnection() {
        Connection conn = null;

        try {
            conn = DBContext.getConnection();
            System.out.println("Kết nối thành công tới Hospital_DB");

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Đã đóng kết nối");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
