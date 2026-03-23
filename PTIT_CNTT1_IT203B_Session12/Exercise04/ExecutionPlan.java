// PHẦN 1: Phân tích sự lãng phí tài nguyên
// Trong đoạn mã lỗi, mỗi lần vòng lặp chạy, một chuỗi SQL mới được tạo ra và gửi đến Database. Điều này gây ra 3 vấn đề nghiêm trọng:

// Hard Parsing (Phân tích cứng) liên tục: Database coi mỗi câu lệnh INSERT INTO Results(data) VALUES('A') và INSERT INTO Results(data) VALUES('B') là hai câu lệnh hoàn toàn khác nhau.
//  Nó buộc phải thực hiện lại toàn bộ quy trình: Kiểm tra cú pháp -> Kiểm tra quyền hạn -> Phân tích ngữ nghĩa.

// Lập kế hoạch thực thi (Execution Plan) 1.000 lần: Database phải tính toán lại cách tối ưu nhất để ghi dữ liệu vào ổ cứng cho từng câu lệnh một, mặc dù cấu trúc chúng y hệt nhau. Đây là hoạt động cực kỳ tốn CPU.
// Tràn bộ nhớ đệm (Shared Pool): Database thường lưu trữ các câu lệnh đã thực thi vào bộ nhớ đệm. Việc gửi 1.000 câu lệnh "rác" (chỉ khác nhau mỗi phần dữ liệu) sẽ làm đẩy các câu lệnh quan trọng khác ra khỏi bộ nhớ đệm, làm chậm toàn bộ hệ thống bệnh viện.

package PTIT_CNTT1_IT203B_Session12.Exercise04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ExecutionPlan {

    /**
     * Lưu danh sách kết quả test vào database
     */
    public static void insertResults(Connection conn, List<TestResult> list) {

        // 1. Chuẩn bị câu lệnh SQL bên ngoài vòng lặp
        String sql = "INSERT INTO Results(data) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 2. Lặp qua danh sách kết quả
            for (TestResult tr : list) {

                // 3. Gán dữ liệu cho tham số
                pstmt.setString(1, tr.getData());

                // 4. Thực thi insert
                pstmt.executeUpdate();
            }

            System.out.println("Thêm dữ liệu Results thành công!");

        } catch (SQLException e) {
            System.err.println("Lỗi khi insert dữ liệu Results!");
            e.printStackTrace();
        }
    }
}
