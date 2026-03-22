package PTIT_CNTT1_IT203B_Session10.Exercise02.src.repository;

import java.sql.Connection;
import java.sql.Statement;

import PTIT_CNTT1_IT203B_Session10.Exercise02.src.db.DBContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyRepository {

    public void printPharmacyCatalogue() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Mở kết nối
            conn = DBContext.getConnection();

            // Tạo Statement
            stmt = conn.createStatement();

            // Thực thi truy vấn
            rs = stmt.executeQuery(
                    "SELECT medicine_name, stock FROM Pharmacy");

            // Duyệt TOÀN BỘ danh sách thuốc
            while (rs.next()) {
                String name = rs.getString("medicine_name");
                int stock = rs.getInt("stock");

                System.out.println(
                        "Thuốc: " + name + " | Tồn kho: " + stock);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
