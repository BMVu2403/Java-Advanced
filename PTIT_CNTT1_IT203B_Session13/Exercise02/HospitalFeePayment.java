// PHẦN 1 - PHÂN TÍCH

// Trong đoạn code, lập trình viên đã tắt Auto-Commit (conn.setAutoCommit(false))
// và chỉ gọi commit() khi cả hai thao tác hoàn tất.

// Tuy nhiên, khi xảy ra lỗi ở bước 2 (ví dụ: sai tên bảng "Invoicesss"),
// câu lệnh ps2.executeUpdate() sẽ ném ra SQLException và chương trình nhảy vào catch.

// Vấn đề là trong khối catch chỉ có System.out.println() để in lỗi,
// mà không có xử lý transaction đang dang dở.

// Theo nguyên tắc của Transaction:
// - Thành công -> commit()
// - Thất bại -> phải rollback()

// Trong trường hợp này:
// - Thao tác 1 (trừ tiền ví) đã chạy thành công
// - Thao tác 2 bị lỗi
// - Nhưng không có rollback()

// Hậu quả:
// - Transaction không được kết thúc rõ ràng
// - Có thể giữ lock trong database
// - Gây lãng phí tài nguyên và ảnh hưởng các giao dịch khác

// Kết luận:
// Thiếu conn.rollback() trong catch là vi phạm nguyên tắc Transaction,
// vì không đảm bảo hệ thống quay về trạng thái an toàn khi có lỗi.

package PTIT_CNTT1_IT203B_Session13.Exercise02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class HospitalFeePayment {

    private static final Statement DatabaseManager = null;

    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        try (Connection conn = DatabaseManager.getConnection()) {

            // Tắt Auto-Commit
            conn.setAutoCommit(false);

            try {
                // Thao tác 1: Trừ tiền trong ví
                String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sqlDeductWallet);
                ps1.setDouble(1, amount);
                ps1.setInt(2, patientId);
                ps1.executeUpdate();

                // Thao tác 2: Cập nhật trạng thái hóa đơn (cố tình sai để test rollback)
                String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sqlUpdateInvoice);
                ps2.setInt(1, invoiceId);
                ps2.executeUpdate();

                // Commit nếu tất cả thành công
                conn.commit();
                System.out.println("Thanh toán hoàn tất!");

            } catch (SQLException e) {

                // BẮT BUỘC rollback khi có lỗi
                try {
                    conn.rollback();
                    System.out.println("Đã rollback giao dịch.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }

                System.out.println("Lỗi hệ thống: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Không thể kết nối DB: " + e.getMessage());
        }
    }
}
