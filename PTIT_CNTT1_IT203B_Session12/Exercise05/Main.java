package PTIT_CNTT1_IT203B_Session12.Exercise05;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/rhms?";
    static final String USER = "root";
    static final String PASS = "24032006";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("""
                            ===== RHMS MENU =====
                            1. Danh sach benh nhan
                            2. Tiep nhan benh nhan
                            3. Cap nhat benh an
                            4. Xuat vien & tinh phi
                            5. Thoat
                        """);

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    // 1. Danh sách bệnh nhân
                    case 1 -> {
                        String sql = "SELECT id, name, age, department FROM Patients";
                        try (PreparedStatement ps = conn.prepareStatement(sql);
                                ResultSet rs = ps.executeQuery()) {

                            while (rs.next()) {
                                System.out.printf(
                                        "%d | %s | %d | %s%n",
                                        rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getInt("age"),
                                        rs.getString("department"));
                            }
                        }
                    }

                    // 2. Tiếp nhận bệnh nhân
                    case 2 -> {
                        String sql = """
                                    INSERT INTO Patients(name, age, department, diagnosis, admit_date)
                                    VALUES (?, ?, ?, ?, CURDATE())
                                """;

                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            System.out.print("Ten: ");
                            ps.setString(1, sc.nextLine()); // xử lý tên có dấu '
                            System.out.print("Tuoi: ");
                            ps.setInt(2, Integer.parseInt(sc.nextLine()));
                            System.out.print("Khoa: ");
                            ps.setString(3, sc.nextLine());
                            System.out.print("Chan doan: ");
                            ps.setString(4, sc.nextLine());
                            ps.executeUpdate();
                            System.out.println("Tiep nhan benh nhan thanh cong!");
                        }
                    }

                    // 3. Cập nhật bệnh án
                    case 3 -> {
                        String sql = "UPDATE Patients SET diagnosis = ? WHERE id = ?";
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            System.out.print("ID benh nhan: ");
                            ps.setInt(2, Integer.parseInt(sc.nextLine()));
                            System.out.print("Benh an moi: ");
                            ps.setString(1, sc.nextLine());
                            ps.executeUpdate();
                            System.out.println("Cap nhat benh án thanh công!");
                        }
                    }

                    // 4. Xuất viện & tính phí
                    case 4 -> {
                        String sql = "{call CALCULATE_DISCHARGE_FEE(?, ?)}";
                        try (CallableStatement cs = conn.prepareCall(sql)) {
                            System.out.print("ID benh nhan: ");
                            cs.setInt(1, Integer.parseInt(sc.nextLine()));
                            cs.registerOutParameter(2, Types.DECIMAL);
                            cs.execute();
                            double fee = cs.getDouble(2);
                            System.out.printf("Tong vien phi: %,.2f VND%n", fee);
                        }
                    }

                    case 5 -> {
                        System.out.println("Thoat chuong trinh.");
                        return;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
