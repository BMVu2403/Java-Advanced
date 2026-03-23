// PHẦN 1:Phân tích - Tại sao PreparedStatement là"Tấm khiên"?

// Mã nguồn trong ảnh của bạn gặp lỗi SQL Injection vì nó sử dụng phương pháp nối chuỗi(String Concatenation).Khi bạn nối trực tiếp biến vào câu lệnh,Database sẽ thực thi bất kỳ ký tự đặc biệt nào có trong biến đó như một phần của lệnh SQL.

// 1. Cơ chế Pre-compiled(Biên dịch trước)Khi sử dụng PreparedStatement,quy trình thực thi câu lệnh SQL thay đổi hoàn toàn:

// Giai đoạn 1(Biên dịch):Câu lệnh SQL với các dấu hỏi chấm(?)được gửi đến Database trước.Database sẽ phân tích cú pháp(parse),tối ưu hóa và biên dịch cấu trúc câu lệnh này.

// Giai đoạn 2(Truyền tham số):Sau khi cấu trúc đã"đóng băng",các giá trị thực tế(như code hay pass)mới được gửi lên.

// 2. Cách nó bảo vệ tham số đầu vào Tách biệt Dữ liệu và Lệnh:Vì câu lệnh đã được biên dịch xong,Database coi các giá trị truyền vào sau đó chỉ là dữ liệu thuần túy(literals).

// Vô hiệu hóa ký tự đặc biệt:Nếu kẻ tấn công nhập' OR '1'='1,Database sẽ không hiểu đây là lệnh logic OR.Thay vào đó,nó sẽ đi tìm một mật khẩu có nội dung chính xác là chuỗi ký tự' OR '1'='1.

// Thoát ký tự(Escaping):PreparedStatement tự động xử lý các dấu nháy đơn hoặc ký tự nguy hiểm,giúp ngăn chặn việc thay đổi cấu trúc logic ban đầu của câu truy vấn.


// PHẦN 2: Thực thi 
package PTIT_CNTT1_IT203B_Session12.Exercise01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDoctor {

    /**
     * Hàm kiểm tra đăng nhập bác sĩ
     */
    public static boolean login(Connection conn, String code, String pass) {

        // 1. Câu lệnh SQL có tham số ?
        String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

        // 2. Sử dụng PreparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 3. Gán giá trị cho các tham số
            pstmt.setString(1, code);
            pstmt.setString(2, pass);

            // 4. Thực thi truy vấn
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Đăng nhập thành công!");
                    return true;
                } else {
                    System.out.println("Sai mã bác sĩ hoặc mật khẩu.");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi đăng nhập bác sĩ!");
            e.printStackTrace();
        }

        return false;
    }
}
