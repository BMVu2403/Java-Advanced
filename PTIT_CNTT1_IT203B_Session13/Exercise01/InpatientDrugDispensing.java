// Phần 1 - Phân tích logic

//Nguyên nhân nằm ở cơ chế Auto-Commit mặc định của JDBC.

//Trong JDBC, khi Connection đang ở chế độ mặc định:

//conn.getAutoCommit() == true

//thì mỗi câu lệnh SQL được xem là một transaction riêng biệt.
//Nghĩa là:

//ps1.executeUpdate() chạy xong thành công
//→ JDBC tự động commit ngay lập tức
//Sau đó chương trình gặp lỗi ở dòng:
//int x = 10 / 0;

//→ phát sinh RuntimeException, luồng xử lý nhảy vào catch

//Vì sao thuốc vẫn bị trừ?

//Vì câu lệnh đầu tiên:

//UPDATE Medicine_Inventory
//SET quantity = quantity - 1
//WHERE medicine_id = ?

//đã được commit ngay sau khi chạy xong, do Auto-Commit đang bật.

//Nên dù phía sau có lỗi, dữ liệu cập nhật kho không còn ở trạng thái chờ nữa, mà đã được ghi thật xuống database.

//Vì sao lịch sử cấp phát không được lưu?

//Vì đoạn code insert lịch sử:

//ps2.executeUpdate();

//nằm sau dòng gây lỗi:

//int x = 10 / 0;

//nên chương trình chưa kịp chạy tới đó.

//Kết quả cuối cùng:

//Kho thuốc đã bị trừ: có
//Lịch sử bệnh án được lưu: không
//Kết luận

// Lý do sai là vì bạn Junior nghĩ rằng:

//cứ đặt 2 lệnh executeUpdate() liên tiếp nhau thì hệ thống sẽ tự hiểu là cùng một nghiệp vụ

//Điều này không đúng trong JDBC.
//Muốn 2 thao tác trở thành một transaction nguyên tử, phải:

//setAutoCommit(false) để tắt commit tự động
//nếu tất cả thành công thì commit()
//nếu có lỗi giữa chừng thì rollback()

package PTIT_CNTT1_IT203B_Session13.Exercise01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class InpatientDrugDispensing {
    private static final Statement DatabaseManager = null;

    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DatabaseManager.getConnection();

            // Tắt auto-commit để gom các câu lệnh thành 1 transaction
            conn.setAutoCommit(false);

            // Thao tác 1: Trừ số lượng thuốc trong kho
            String sqlUpdateInventory = "UPDATE Medicine_Inventory " +
                    "SET quantity = quantity - 1 " +
                    "WHERE medicine_id = ?";

            ps1 = conn.prepareStatement(sqlUpdateInventory);
            ps1.setInt(1, medicineId);
            int row1 = ps1.executeUpdate();

            if (row1 == 0) {
                throw new Exception("Không tìm thấy thuốc trong kho để cập nhật.");
            }

            // Thao tác 2: Ghi vào lịch sử bệnh án
            String sqlInsertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) " +
                    "VALUES (?, ?, GETDATE())";

            ps2 = conn.prepareStatement(sqlInsertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            int row2 = ps2.executeUpdate();

            if (row2 == 0) {
                throw new Exception("Không thể lưu lịch sử cấp phát thuốc.");
            }

            // Nếu cả 2 thao tác đều thành công -> commit
            conn.commit();
            System.out.println("Cấp phát thuốc thành công!");

        } catch (Exception e) {
            // Nếu có lỗi ở bất kỳ bước nào -> rollback toàn bộ
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback transaction.");
                } catch (Exception rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }

            System.out.println("Có lỗi xảy ra: " + e.getMessage());

        } finally {
            // Đóng tài nguyên theo thứ tự ngược lại
            try {
                if (ps2 != null)
                    ps2.close();
            } catch (Exception e) {
                System.out.println("Lỗi đóng ps2: " + e.getMessage());
            }

            try {
                if (ps1 != null)
                    ps1.close();
            } catch (Exception e) {
                System.out.println("Lỗi đóng ps1: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    // Bật lại auto-commit để tránh ảnh hưởng các chỗ khác
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Lỗi đóng connection: " + e.getMessage());
            }
        }
    }
}