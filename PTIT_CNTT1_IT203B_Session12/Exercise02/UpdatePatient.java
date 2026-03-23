
// PHẦN 1: Phân tích - Tại sao setDouble() và setInt() giải quyết được vấn đề?

// Khi bạn nối chuỗi (" ... " + temp), Java sẽ tự động gọi hàm toString() của biến temp. 
// Hàm này phụ thuộc vào cài đặt vùng miền (Locale) của hệ điều hành.
// Ở Mỹ/Anh: $37.5$Ở Việt Nam/Pháp: $37,5$Nếu SQL nhận được dấu phẩy (,), nó sẽ hiểu lầm đó là dấu ngăn cách giữa các cột thay vì dấu thập phân, dẫn đến lỗi Syntax Error.

// Tại sao PreparedStatement lại an toàn?Truyền dữ liệu nhị phân (Binary Transfer):
//  Khi dùng setDouble(index, value), giá trị $37.5$ được gửi đến Database dưới dạng giá trị số thuần túy (binary/numeric value), không phải dưới dạng chuỗi văn bản ("37.5" hay "37,5").
// Độc lập với Locale: Vì dữ liệu không bị ép kiểu sang chuỗi ở phía ứng dụng Java, nên dấu chấm hay dấu phẩy của hệ điều hành không còn ý nghĩa.
// Database Driver tự xử lý: Trình điều khiển JDBC (Driver) sẽ chịu trách nhiệm giao tiếp với Database theo đúng giao thức mà Database hiểu, đảm bảo giá trị số được lưu chính xác vào cột có kiểu dữ liệu tương ứng (ví dụ: DECIMAL hoặc DOUBLE trong SQL).


package PTIT_CNTT1_IT203B_Session12.Exercise02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePatient {

    /**
     * Cập nhật chỉ số sinh tồn của bệnh nhân
     */
    public static void updateVitals(Connection conn, int patientId, double temp, int heartRate) {

        // 1. Câu lệnh SQL với tham số ?
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 2. Gán giá trị cho các tham số
            pstmt.setDouble(1, temp); // Nhiệt độ
            pstmt.setInt(2, heartRate); // Nhịp tim
            pstmt.setInt(3, patientId); // ID bệnh nhân

            // 3. Thực thi cập nhật
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật chỉ số sinh tồn thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân với ID: " + patientId);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chỉ số sinh tồn!");
            e.printStackTrace();
        }
    }
}
