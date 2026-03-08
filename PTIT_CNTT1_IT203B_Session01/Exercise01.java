import java.util.Scanner;

public class Exercise01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhap nam sinh cua ban: ");
            String namSinhStr = sc.nextLine();

            int namSinh = Integer.parseInt(namSinhStr);

            int namHienTai = 2026;
            int tuoi = namHienTai - namSinh;

            System.out.println("Tuoi cua ban la: " + tuoi);
        } 
        catch (NumberFormatException e) {
            System.out.println("Loi: Ban phai nhap nam sinh bang so. Vi du: 2002");
        } 
        finally {
            sc.close();
            System.out.println("Thuc hien don dep tai nguyen trong finally...");
        }
    }
}
