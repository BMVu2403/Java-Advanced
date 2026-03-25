package PTIT_CNTT1_IT203B_Session14;

import java.sql.*;

public class TransferMoney {

    static final String URL = "jdbc:mysql://localhost:3306/rhms";
    static final String USER = "root";
    static final String PASSWORD = "24032006";

    public static void main(String[] args) {

        String fromId = "ACC01";
        String toId = "ACC02";
        double amount = 1000;

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // bat dau transaction
            conn.setAutoCommit(false);

            // kiem tra so du tai khoan gui
            String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            PreparedStatement ps = conn.prepareStatement(checkSql);
            ps.setString(1, fromId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new Exception("tai khoan gui khong ton tai");
            }

            double balance = rs.getDouble("Balance");

            if (balance < amount) {
                throw new Exception("khong du tien");
            }

            // tru tien tai khoan A
            CallableStatement cs1 = conn.prepareCall("{CALL sp_UpdateBalance(?, ?)}");
            cs1.setString(1, fromId);
            cs1.setDouble(2, -amount);
            cs1.execute();

            // cong tien tai khoan B
            CallableStatement cs2 = conn.prepareCall("{CALL sp_UpdateBalance(?, ?)}");
            cs2.setString(1, toId);
            cs2.setDouble(2, amount);
            cs2.execute();

            // commit
            conn.commit();
            System.out.println("chuyen khoan thanh cong");

            // hien thi ket qua
            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM Accounts");
            ResultSet rs2 = ps2.executeQuery();

            System.out.println("danh sach tai khoan:");
            while (rs2.next()) {
                System.out.println(
                        rs2.getString("AccountId") + " | " +
                                rs2.getString("FullName") + " | " +
                                rs2.getDouble("Balance"));
            }

        } catch (Exception e) {
            System.out.println("loi: " + e.getMessage());

            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("rollback thanh cong");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}