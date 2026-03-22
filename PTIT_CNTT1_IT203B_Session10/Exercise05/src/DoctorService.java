package PTIT_CNTT1_IT203B_Session10.Exercise05.src;

import PTIT_CNTT1_IT203B_Session10.Exercise05.src.repository.DoctorRepository;

public class DoctorService {

    private final DoctorRepository repo = new DoctorRepository();

    public void xemDanhSach() {
        repo.getAllDoctors();
    }

    public void themBacSi(String id, String name, String specialty) {
        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty()) {
            System.out.println("Khong duoc de trong du lieu");
            return;
        }

        Doctor doctor = new Doctor(id, name, specialty);
        repo.insertDoctor(doctor);
    }

    public void thongKeChuyenKhoa() {
        repo.countBySpecialty();
    }
}
