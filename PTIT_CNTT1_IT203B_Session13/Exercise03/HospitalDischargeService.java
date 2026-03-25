// 1. PHÂN TÍCH BÀI TOÁN (I/O)

// Input:
// - maBenhNhan (int): mã bệnh nhân cần xuất viện
// - tienVienPhi (double): số tiền viện phí cần thanh toán

// Output:
// - Nếu thành công: thông báo "Xuất viện và thanh toán thành công"
// - Nếu thất bại: thông báo lỗi và rollback toàn bộ dữ liệu

// 2. MÔ TẢ NGHIỆP VỤ

// Quy trình xuất viện gồm 3 bước:
// 1. Trừ tiền viện phí vào số dư tạm ứng
// 2. Giải phóng giường bệnh (status = 'TRONG')
// 3. Cập nhật trạng thái bệnh nhân = 'DA_XUAT_VIEN'

// Yêu cầu:
// 3 bước này phải nằm trong 1 Transaction
// => Thành công tất cả hoặc rollback tất cả (All or Nothing)

// 3. ĐỀ XUẤT GIẢI PHÁP

// - Sử dụng JDBC Connection
// - Tắt AutoCommit bằng conn.setAutoCommit(false)
// - Thực hiện lần lượt 3 câu lệnh UPDATE
// - Nếu tất cả thành công => commit()
// - Nếu có lỗi bất kỳ => rollback()

// Đồng thời xử lý 2 bẫy dữ liệu:

// Bẫy 1 - Thiếu tiền:
// - Trước khi trừ tiền phải SELECT số dư
// - Nếu số dư < tiền viện phí => throw Exception => rollback

// Bẫy 2 - Row Affected = 0:
// - executeUpdate() trả về 0 nghĩa là không có dòng nào được cập nhật
// - JDBC không tự báo lỗi
// - Phải tự kiểm tra và throw Exception => rollback

// 4. THIẾT KẾ CÁC BƯỚC XỬ LÝ

// Bước 1: Mở kết nối database
// Bước 2: Tắt AutoCommit

// Bước 3: SELECT số dư + mã giường
// - Nếu không có dữ liệu => throw Exception (bệnh nhân không tồn tại)

// Bước 4: Kiểm tra số dư
// - Nếu số dư < viện phí => throw Exception (Bẫy 1)

// Bước 5: UPDATE trừ tiền
// - Nếu row = 0 => throw Exception (Bẫy 2)

// Bước 6: UPDATE giường về TRONG
// - Nếu row = 0 => throw Exception (Bẫy 2)

// Bước 7: UPDATE trạng thái bệnh nhân
// - Nếu row = 0 => throw Exception (Bẫy 2)

// Bước 8: Nếu tất cả OK => commit()

// Bước 9: Nếu có lỗi => rollback()

// Bước 10: Đóng toàn bộ tài nguyên (ResultSet, PreparedStatement, Connection)

// 5. KẾT LUẬN

// Giải pháp sử dụng Transaction đảm bảo:
// - Không xảy ra tình trạng trừ tiền nhưng chưa xuất viện
// - Không bị treo giường bệnh
// - Dữ liệu luôn nhất quán (consistency)

// Nguyên tắc chính:
// => Thành công tất cả hoặc rollback tất cả (All or Nothing)

package PTIT_CNTT1_IT203B_Session13.Exercise03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HospitalDischargeService {

    public static final Statement DatabaseManager = null;

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdateBalance = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psUpdatePatient = null;
        ResultSet rs = null;

        try {
            conn = DatabaseManager.getConnection();

            // Tắt AutoCommit để gom 3 thao tác vào cùng 1 transaction
            conn.setAutoCommit(false);

            // Lấy số dư tạm ứng và mã giường của bệnh nhân
            String sqlSelect = "SELECT advance_balance, bed_id FROM Patients WHERE patient_id = ?";
            psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, maBenhNhan);
            rs = psSelect.executeQuery();

            // BẪY 2:
            // Nếu không tìm thấy bệnh nhân thì chủ động ném lỗi để rollback
            if (!rs.next()) {
                throw new Exception("Không tìm thấy bệnh nhân.");
            }

            double soDuTamUng = rs.getDouble("advance_balance");
            int maGiuong = rs.getInt("bed_id");

            // BẪY 1:
            // Không cho phép số dư bị âm
            // Nếu số dư < tiền viện phí thì ném lỗi và rollback
            if (soDuTamUng < tienVienPhi) {
                throw new Exception("Số dư tạm ứng không đủ để thanh toán viện phí.");
            }

            // Bước 1: Trừ tiền viện phí vào số dư tạm ứng
            String sqlUpdateBalance = "UPDATE Patients SET advance_balance = advance_balance - ? WHERE patient_id = ?";
            psUpdateBalance = conn.prepareStatement(sqlUpdateBalance);
            psUpdateBalance.setDouble(1, tienVienPhi);
            psUpdateBalance.setInt(2, maBenhNhan);

            int rowBalance = psUpdateBalance.executeUpdate();

            // BẪY 2:
            // Nếu executeUpdate() trả về 0 thì không có dòng nào được cập nhật
            // Phải tự ném lỗi để rollback
            if (rowBalance == 0) {
                throw new Exception("Không thể cập nhật số dư tạm ứng.");
            }

            // Bước 2: Giải phóng giường bệnh
            String sqlUpdateBed = "UPDATE Beds SET status = 'TRONG' WHERE bed_id = ?";
            psUpdateBed = conn.prepareStatement(sqlUpdateBed);
            psUpdateBed.setInt(1, maGiuong);

            int rowBed = psUpdateBed.executeUpdate();

            // BẪY 2:
            // Nếu không cập nhật được giường thì rollback
            if (rowBed == 0) {
                throw new Exception("Không thể cập nhật trạng thái giường bệnh.");
            }

            // Bước 3: Cập nhật trạng thái bệnh nhân thành đã xuất viện
            String sqlUpdatePatient = "UPDATE Patients SET status = 'DA_XUAT_VIEN' WHERE patient_id = ?";
            psUpdatePatient = conn.prepareStatement(sqlUpdatePatient);
            psUpdatePatient.setInt(1, maBenhNhan);

            int rowPatient = psUpdatePatient.executeUpdate();

            // BẪY 2:
            // Nếu không cập nhật được trạng thái bệnh nhân thì rollback
            if (rowPatient == 0) {
                throw new Exception("Không thể cập nhật trạng thái xuất viện của bệnh nhân.");
            }

            // Nếu cả 3 bước đều thành công thì commit
            conn.commit();
            System.out.println("Xuất viện và thanh toán thành công!");

        } catch (Exception e) {
            // Nếu có lỗi thì rollback toàn bộ transaction
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback transaction.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }

            System.out.println("Xuất viện thất bại: " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng ResultSet: " + e.getMessage());
            }

            try {
                if (psSelect != null) {
                    psSelect.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psSelect: " + e.getMessage());
            }

            try {
                if (psUpdateBalance != null) {
                    psUpdateBalance.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psUpdateBalance: " + e.getMessage());
            }

            try {
                if (psUpdateBed != null) {
                    psUpdateBed.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psUpdateBed: " + e.getMessage());
            }

            try {
                if (psUpdatePatient != null) {
                    psUpdatePatient.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psUpdatePatient: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng Connection: " + e.getMessage());
            }
        }
    }
}
