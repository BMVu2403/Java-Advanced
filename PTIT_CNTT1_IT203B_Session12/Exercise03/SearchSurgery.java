// PHẦN 1: Phân tích - Vai trò của registerOutParameter()
//Khác với PreparedStatement chỉ gửi dữ liệu đi, CallableStatement với tham số OUT yêu cầu một sự phối hợp hai chiều giữa Java và Database.

// 1. Tại sao bắt buộc phải gọi registerOutParameter()?
// Cấp phát bộ nhớ: JDBC cần biết trước kiểu dữ liệu của tham số đầu ra để chuẩn bị vùng bộ nhớ (buffer) phù hợp nhằm hứng kết quả trả về từ Database sau khi thực thi xong.

// Ép kiểu dữ liệu (Mapping): Database có các kiểu dữ liệu riêng (như NUMBER, VARCHAR2, DECIMAL). Hàm này giúp Java hiểu rằng: "Hãy chuẩn bị tinh thần để nhận một giá trị kiểu X từ vị trí dấu hỏi chấm thứ Y".

// Luồng thực thi: Nếu không đăng ký, khi bạn gọi getDouble(2), JDBC sẽ không tìm thấy bất kỳ định nghĩa nào cho vị trí đó trong bộ nhớ đệm và gây ra lỗi column index out of range hoặc Parameter not registered.

// 2. Đăng ký kiểu DECIMAL trong Java
// Nếu tham số đầu ra trong SQL là kiểu DECIMAL, trong Java bạn phải sử dụng hằng số sau từ lớp java.sql.Types:

// java.sql.Types.DECIMAL (hoặc đôi khi dùng java.sql.Types.NUMERIC tùy vào cấu trúc DB).

package PTIT_CNTT1_IT203B_Session12.Exercise03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class SearchSurgery {

    public static void main(String[] args) {

        int surgeryId = 505;

        // Cú pháp gọi Stored Procedure
        String sql = "{call GET_SURGERY_FEE(?, ?)}";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:your_db_url", "user", "pass");
                CallableStatement cstmt = conn.prepareCall(sql)) {

            // 1. Gán tham số đầu vào (IN)
            cstmt.setInt(1, surgeryId);

            // 2. Đăng ký tham số đầu ra (OUT)
            cstmt.registerOutParameter(2, Types.DECIMAL);

            // 3. Thực thi Stored Procedure
            cstmt.execute();

            // 4. Lấy giá trị OUT sau khi thực thi
            double totalCost = cstmt.getDouble(2);

            System.out.println("=== THÔNG TIN CHI PHÍ PHẪU THUẬT ===");
            System.out.println("Mã ca phẫu thuật: " + surgeryId);
            System.out.printf("Tổng chi phí: %,.2f VND%n", totalCost);

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn chi phí phẫu thuật!");
            e.printStackTrace();
        }
    }
}
