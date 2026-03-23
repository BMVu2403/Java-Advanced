package PTIT_CNTT1_IT203B_Session11.Exercise03.src.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import PTIT_CNTT1_IT203B_Session11.Exercise03.src.db.DBConnection;

public class BedRepository {

    public int updateBedStatus(String bedId) throws SQLException {
        String sql = "UPDATE bed SET bed_status = 'Dang su dung' WHERE bed_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bedId);
            return ps.executeUpdate();
        }
    }
}
