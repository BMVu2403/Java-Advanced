package PTIT_CNTT1_IT203B_Session11.Exercise03;

import PTIT_CNTT1_IT203B_Session11.Exercise03.src.repository.BedRepository;

public class Main {

    public static void main(String[] args) {

        BedRepository bedRepository = new BedRepository();

        capNhatGiuong(bedRepository, "Bed_001");
        capNhatGiuong(bedRepository, "Bed_999");
    }

    private static void capNhatGiuong(BedRepository repo, String bedId) {
        try {
            int rows = repo.updateBedStatus(bedId);

            if (rows == 0) {
                System.out.println("Ma giuong khong ton tai: " + bedId);
            } else {
                System.out.println("Cap nhat giuong thanh cong: " + bedId);
            }

        } catch (Exception e) {
            System.out.println("Loi he thong");
        }
    }
}
