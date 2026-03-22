package PTIT_CNTT1_IT203B_Session10.Exercise05.src.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import PTIT_CNTT1_IT203B_Session10.Exercise05.src.Doctor;
import PTIT_CNTT1_IT203B_Session10.Exercise05.src.db.DBConnection;

public class DoctorRepository {

    public void getAllDoctors() {
        String sql = "SELECT doctor_id, full_name, specialty FROM doctors";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(
                        rs.getString("doctor_id") + " | " +
                                rs.getString("full_name") + " | " +
                                rs.getString("specialty"));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach bac si");
        }
    }

    public void insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors VALUES (?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctor.getDoctorId());
            ps.setString(2, doctor.getFullName());
            ps.setString(3, doctor.getSpecialty());

            ps.executeUpdate();
            System.out.println("Them bac si thanh cong");

        } catch (Exception e) {
            System.out.println("Loi them bac si (trung ma hoac du lieu qua dai)");
        }
    }

    public void countBySpecialty() {
        String sql = """
                    SELECT specialty, COUNT(*) AS total
                    FROM doctors
                    GROUP BY specialty
                """;

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(
                        rs.getString("specialty") + ": " +
                                rs.getInt("total"));
            }
        } catch (Exception e) {
            System.out.println("Loi thong ke chuyen khoa");
        }
    }
}
