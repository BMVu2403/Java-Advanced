// 1. PHÂN TÍCH RỦI RO & BẪY LỖI (EDGE CASES)


// Kịch bản 1:
// Nhân viên nhập sai kiểu dữ liệu, ví dụ:
// - nhập "abc" vào tuổi
// - nhập "Năm trăm" vào tiền tạm ứng
// Nếu không validate input, chương trình có thể bị crash do InputMismatchException
// hoặc NumberFormatException.

// Kịch bản 2:
// Nhân viên chọn mã giường không tồn tại hoặc giường đó đã có người.
// Nếu không kiểm tra trước khi cập nhật, hệ thống có thể:
// - thêm bệnh nhân thành công
// - nhưng không xếp được giường
// => dữ liệu bị lệch nếu không dùng Transaction.

// Kịch bản 3:
// Thêm bệnh nhân thành công, cập nhật giường thành công,
// nhưng lỗi ở bước cộng tiền vào bảng tài chính do mất mạng / sai SQL.
// Nếu không rollback:
// - bệnh nhân đã có hồ sơ
// - giường đã bị chiếm
// - nhưng tài chính chưa ghi nhận
// => sai dữ liệu nghiêm trọng.

// Kịch bản 4:
// Nhân viên nhập số tiền âm hoặc bằng 0.
// Nếu không validate, hệ thống có thể ghi sai quỹ tài chính hoặc tạo hồ sơ không hợp lệ.

// Kịch bản 5:
// Hai nhân viên cùng lúc chọn cùng một giường trống.
// Nếu không kiểm tra điều kiện cập nhật chặt chẽ trong SQL,
// có thể xảy ra trùng giường.

// Kết luận:
// Chức năng tiếp nhận bệnh nhân bắt buộc phải dùng JDBC Transaction:
// - setAutoCommit(false)
// - commit nếu mọi bước thành công
// - rollback nếu có bất kỳ lỗi nào.

// 2. THIẾT KẾ KIẾN TRÚC (ARCHITECTURE & DATA FLOW)

// Cấu trúc lớp đề xuất:

// 1. ReceptionView
// - Hiển thị menu console
// - Nhập dữ liệu từ người dùng
// - Gọi controller xử lý

// 2. PatientController
// - Điều phối logic nghiệp vụ
// - Gọi BedService / AdmissionService

// 3. AdmissionService
// - Xử lý transaction tiếp nhận bệnh nhân
// - Thêm bệnh nhân
// - Cập nhật trạng thái giường
// - Ghi nhận tài chính

// 4. DatabaseHelper
// - Quản lý kết nối database

// 5. Model:
// - Bed
// - Patient

// 3. FLOW XỬ LÝ CHỨC NĂNG "TIẾP NHẬN BỆNH NHÂN"

// Bước 1: Hiển thị form nhập:
// - Tên bệnh nhân
// - Tuổi
// - Mã giường
// - Số tiền tạm ứng

// Bước 2: Validate dữ liệu nhập
// - Tên không được rỗng
// - Tuổi > 0
// - Tiền tạm ứng > 0

// Bước 3: Mở kết nối DB
// Bước 4: Tắt AutoCommit

// Bước 5: Kiểm tra giường có tồn tại và đang TRONG hay không
// - Nếu không hợp lệ => throw Exception

// Bước 6: INSERT bệnh nhân mới

// Bước 7: UPDATE giường thành DA_CO_NGUOI
// - Nếu rowAffected = 0 => throw Exception

// Bước 8: INSERT bản ghi tài chính nhận tiền tạm ứng

// Bước 9: Nếu tất cả thành công => commit

// Bước 10: Nếu có lỗi bất kỳ => rollback

// Bước 11: Đóng tài nguyên và in thông báo thân thiện

// 4. THIẾT KẾ DATABASE (ĐƠN GIẢN)

// Bảng Beds
// - bed_id INT PRIMARY KEY
// - bed_name VARCHAR(50)
// - status VARCHAR(20)        // TRONG / DA_CO_NGUOI

// Bảng Patients
// - patient_id INT IDENTITY PRIMARY KEY
// - patient_name VARCHAR(100)
// - age INT
// - bed_id INT
// - advance_amount DOUBLE
// - status VARCHAR(30)        // NOI_TRU

// Bảng Finance_Records
// - record_id INT IDENTITY PRIMARY KEY
// - patient_id INT
// - amount DOUBLE
// - record_type VARCHAR(30)   // TAM_UNG
// - created_at DATETIME


package PTIT_CNTT1_IT203B_Session13.Exercise05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/rhms";
    private static final String USER = "root";
    private static final String PASSWORD = "24032006";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}