package PTIT_CNTT1_IT203B_Session11.Exercise04.src.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import PTIT_CNTT1_IT203B_Session11.Exercise04.src.db.DBConnection;
import PTIT_CNTT1_IT203B_Session11.Exercise04.src.db.InputValidator;

public class PatientRepository {

    public void findPatientByName(String name) {
        try {
            String safeName = InputValidator.sanitize(name);

            String sql = "SELECT * FROM patient WHERE name = '" + safeName + "'";

            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        rs.getString("patient_id") + " - " +
                                rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
