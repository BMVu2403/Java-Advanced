// 1. PHÂN TÍCH INPUT / OUTPUT

// Input:
// - Hệ thống cần lấy danh sách tất cả bệnh nhân đang nằm tại khoa trong ngày
// - Có thể lọc theo trạng thái đang điều trị, khoa hiện tại, ngày hiện tại...
// - Trong bài này có thể hiểu đầu vào chính là dữ liệu từ DB,
//   hoặc một tham số như maKhoa / ngayHienTai nếu cần mở rộng

// Output:
// - Trả về List<BenhNhanDTO>
// - Mỗi BenhNhanDTO gồm:
//   + thông tin bệnh nhân
//   + danh sách dịch vụ/thuốc đã sử dụng: List<DichVu>

// Mục tiêu:
// - Không bị chậm khi số lượng bệnh nhân khoảng 500 người
// - Không bỏ sót bệnh nhân chưa có dịch vụ nào
// - Không phát sinh NullPointerException khi map dữ liệu

// 2. PHÂN TÍCH NGUYÊN NHÂN CHẬM - N+1 QUERY

// Kiểu code cũ thường là:
// Bước 1: query lấy danh sách bệnh nhân
// Bước 2: với mỗi bệnh nhân lại query tiếp để lấy danh sách dịch vụ

// Ví dụ có 500 bệnh nhân:
// - 1 query lấy danh sách bệnh nhân
// - 500 query lấy dịch vụ cho từng bệnh nhân
// Tổng = 501 query

// Đây chính là N+1 Query Problem
// Hậu quả:
// - Tăng rất nhiều số lần gọi DB
// - Tăng Network I/O
// - Tăng thời gian chờ
// - Dashboard load rất chậm

// 3. ĐỀ XUẤT 2 GIẢI PHÁP JDBC

// GIẢI PHÁP 1: DÙNG 1 CÂU LEFT JOIN DUY NHẤT
// Ý tưởng:
// - Dùng 1 câu SQL JOIN bảng BenhNhan và DichVuSuDung
// - Duyệt ResultSet
// - Gom các dòng cùng maBenhNhan thành 1 BenhNhanDTO
// - Mỗi dòng nếu có dữ liệu dịch vụ thì add vào dsDichVu

// Ưu điểm:
// - Chỉ bắn 1 query xuống DB
// - Network I/O thấp nhất
// - Khắc phục triệt để N+1
// - Hiệu năng rất tốt nếu dữ liệu không quá lớn

// Nhược điểm:
// - Dữ liệu bệnh nhân sẽ bị lặp trên nhiều dòng do quan hệ 1-N
// - Java phải gom nhóm lại bằng Map
// - Code xử lý ResultSet khó hơn cách đơn giản

// GIẢI PHÁP 2: DÙNG 2 QUERY
// Ý tưởng:
// - Query 1: lấy toàn bộ danh sách bệnh nhân
// - Query 2: lấy toàn bộ dịch vụ của các bệnh nhân đó
// - Sau đó dùng Map<Integer, BenhNhanDTO> để gắn dịch vụ vào đúng bệnh nhân

// Ưu điểm:
// - Vẫn tránh được N+1 vì chỉ dùng 2 query
// - Code dễ hiểu hơn JOIN trong một số trường hợp
// - Tránh việc lặp quá nhiều cột bệnh nhân trên ResultSet JOIN

// Nhược điểm:
// - Phải xử lý 2 lần đọc dữ liệu
// - Cần tổ chức danh sách id bệnh nhân hoặc query đủ rộng
// - Số query nhiều hơn giải pháp 1
// - Code hơi dài hơn

// 4. SO SÁNH 2 GIẢI PHÁP

// Tiêu chí 1 - Số lượng query / Network I/O
// - Giải pháp 1: 1 query
// - Giải pháp 2: 2 query
// => Giải pháp 1 tốt hơn

// Tiêu chí 2 - Xử lý trên RAM Java
// - Giải pháp 1: phải gom nhóm dữ liệu từ ResultSet JOIN
// - Giải pháp 2: cũng phải map dữ liệu nhưng tách riêng bệnh nhân và dịch vụ
// => Cả hai đều cần RAM, nhưng giải pháp 2 đôi khi dễ kiểm soát hơn

// Tiêu chí 3 - Độ dễ code
// - Giải pháp 1: khó hơn vì phải xử lý dữ liệu lặp sau JOIN
// - Giải pháp 2: dễ đọc, dễ bảo trì hơn
// => Giải pháp 2 dễ code hơn

// Tiêu chí 4 - Hiệu năng thực tế cho 500 bệnh nhân
// - Giải pháp 1: thường nhanh nhất vì chỉ có 1 lần round-trip tới DB
// - Giải pháp 2: vẫn nhanh, nhưng nhiều hơn 1 query
// => Giải pháp 1 tối ưu hơn cho dashboard tải nhanh

// 5. CHỐT GIẢI PHÁP TỐI ƯU

// Chọn GIẢI PHÁP 1: dùng 1 câu LEFT JOIN duy nhất

// Lý do:
// - Chỉ dùng 1 query
// - Giảm tối đa Network I/O
// - Phù hợp yêu cầu response time < 1 giây
// - LEFT JOIN giúp không bỏ sót bệnh nhân chưa có dịch vụ
// - Rất phù hợp cho màn hình dashboard đọc nhiều, cần tốc độ cao

// 6. THIẾT KẾ SQL VÀ CÁC BƯỚC XỬ LÝ

// Câu SQL:
// SELECT thông tin bệnh nhân từ BenhNhan
// LEFT JOIN DichVuSuDung theo maBenhNhan
// WHERE lọc các bệnh nhân đang nằm tại khoa
// ORDER BY maBenhNhan

// Vì dùng LEFT JOIN:
// - Nếu bệnh nhân chưa có dịch vụ,
//   dữ liệu phía bảng DichVuSuDung sẽ là NULL
// - Nhưng bệnh nhân vẫn xuất hiện trên ResultSet

// Các bước xử lý trên Java:
// Bước 1: Mở connection
// Bước 2: Thực thi câu SQL LEFT JOIN
// Bước 3: Tạo Map<Integer, BenhNhanDTO>
// Bước 4: Duyệt từng dòng ResultSet
// Bước 5: Nếu maBenhNhan chưa có trong Map -> tạo mới BenhNhanDTO
// Bước 6: Kiểm tra cột maDichVu có NULL hay không
//         - Nếu có dữ liệu thì tạo DichVu và add vào dsDichVu
//         - Nếu NULL thì bỏ qua, để dsDichVu rỗng
//         => Đây chính là xử lý Bẫy 2
// Bước 7: Sau khi duyệt xong, trả về new ArrayList<>(map.values())
// Bước 8: Đóng ResultSet / PreparedStatement / Connection

// 7. LƯU Ý QUAN TRỌNG CHO BẪY 2

// Không được dùng INNER JOIN
// Vì INNER JOIN sẽ bỏ mất bệnh nhân chưa có dịch vụ

// Phải dùng LEFT JOIN
// Và khi map dữ liệu phải kiểm tra cột dịch vụ có NULL không
// để tránh:
// - bỏ sót bệnh nhân
// - NullPointerException

package PTIT_CNTT1_IT203B_Session13.Exercise04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NurseDashboardService {

    private static final Statement DatabaseManager = null;

    public List<BenhNhanDTO> layDanhSachDashboard() {
        List<BenhNhanDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseManager.getConnection();

            String sql = "SELECT b.maBenhNhan, b.tenBenhNhan, b.tuoi, b.soGiuong, "
                    + "d.maDichVu, d.tenDichVu, d.loaiDichVu, d.soLuong "
                    + "FROM BenhNhan b "
                    + "LEFT JOIN DichVuSuDung d ON b.maBenhNhan = d.maBenhNhan "
                    + "WHERE b.trangThai = 'DANG_DIEU_TRI' "
                    + "ORDER BY b.maBenhNhan";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            Map<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();

            while (rs.next()) {
                int maBenhNhan = rs.getInt("maBenhNhan");

                BenhNhanDTO benhNhan = mapBenhNhan.get(maBenhNhan);
                if (benhNhan == null) {
                    benhNhan = new BenhNhanDTO();
                    benhNhan.setMaBenhNhan(maBenhNhan);
                    benhNhan.setTenBenhNhan(rs.getString("tenBenhNhan"));
                    benhNhan.setTuoi(rs.getInt("tuoi"));
                    benhNhan.setSoGiuong(rs.getString("soGiuong"));
                    benhNhan.setDsDichVu(new ArrayList<>());

                    mapBenhNhan.put(maBenhNhan, benhNhan);
                }

                // XỬ LÝ BẪY 2:
                // Nếu bệnh nhân chưa có dịch vụ thì các cột bên bảng DichVuSuDung sẽ là NULL.
                // Khi đó không tạo đối tượng DichVu, chỉ giữ dsDichVu là danh sách rỗng.
                int maDichVu = rs.getInt("maDichVu");
                if (!rs.wasNull()) {
                    DichVu dichVu = new DichVu();
                    dichVu.setMaDichVu(maDichVu);
                    dichVu.setTenDichVu(rs.getString("tenDichVu"));
                    dichVu.setLoaiDichVu(rs.getString("loaiDichVu"));
                    dichVu.setSoLuong(rs.getInt("soLuong"));

                    benhNhan.getDsDichVu().add(dichVu);
                }
            }

            result = new ArrayList<>(mapBenhNhan.values());

        } catch (SQLException e) {
            System.out.println("Loi khi lay du lieu dashboard: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong ResultSet: " + e.getMessage());
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong PreparedStatement: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong Connection: " + e.getMessage());
            }
        }

        return result;
    }
}