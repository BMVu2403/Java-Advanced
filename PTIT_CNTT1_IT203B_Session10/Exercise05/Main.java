package PTIT_CNTT1_IT203B_Session10.Exercise05;

import java.util.Scanner;

import PTIT_CNTT1_IT203B_Session10.Exercise05.src.DoctorService;

public class Main {

    public static void main(String[] args) {

        DoctorService service = new DoctorService();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== RIKKEI CARE ===");
                System.out.println("1. Xem danh sach bac si");
                System.out.println("2. Them bac si moi");
                System.out.println("3. Thong ke chuyen khoa");
                System.out.println("4. Thoat");
                System.out.print("Chon: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        service.xemDanhSach();
                        break;
                    case 2:
                        System.out.print("Ma bac si: ");
                        String id = sc.nextLine();
                        System.out.print("Ho ten: ");
                        String name = sc.nextLine();
                        System.out.print("Chuyen khoa: ");
                        String specialty = sc.nextLine();
                        service.themBacSi(id, name, specialty);
                        break;
                    case 3:
                        service.thongKeChuyenKhoa();
                        break;
                    case 4:
                        System.out.println("Thoat chuong trinh");
                        return;
                    default:
                        System.out.println("Lua chon khong hop le");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
