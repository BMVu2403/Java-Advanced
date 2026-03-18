package PTIT_CNTT1_IT203B_Session09.MiniProject;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductDatabase db = ProductDatabase.getInstance();

        while (true) {
            System.out.println("\n------ QUAN LY SAN PHAM ------");
            System.out.println("1. Them san pham");
            System.out.println("2. Xem danh sach");
            System.out.println("3. Cap nhat san pham");
            System.out.println("4. Xoa san pham");
            System.out.println("5. Thoat");
            System.out.print("Lua chon: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Nhap sai!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("1. Physical");
                    System.out.println("2. Digital");
                    System.out.print("Chon loai: ");
                    int type = Integer.parseInt(sc.nextLine());

                    Product p = ProductFactory.createProduct(type, sc);
                    if (p != null) {
                        db.addProduct(p);
                        System.out.println("Them thanh cong!");
                    }
                    break;

                case 2:
                    List<Product> list = db.getAll();
                    if (list.isEmpty()) {
                        System.out.println("Danh sach rong!");
                    } else {
                        for (Product pr : list) {
                            pr.displayInfo();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Nhap ID: ");
                    String idUpdate = sc.nextLine();
                    Product product = db.findById(idUpdate);

                    if (product == null) {
                        System.out.println("Khong tim thay!");
                        break;
                    }

                    System.out.print("Ten moi: ");
                    product.setName(sc.nextLine());

                    System.out.print("Gia moi: ");
                    product.setPrice(Double.parseDouble(sc.nextLine()));

                    if (product instanceof PhysicalProduct) {
                        System.out.print("Weight moi: ");
                        ((PhysicalProduct) product).setWeight(Double.parseDouble(sc.nextLine()));
                    } else if (product instanceof DigitalProduct) {
                        System.out.print("Size moi: ");
                        ((DigitalProduct) product).setSize(Double.parseDouble(sc.nextLine()));
                    }

                    System.out.println("Cap nhat thanh cong!");
                    break;

                case 4:
                    System.out.print("Nhap ID: ");
                    String idDelete = sc.nextLine();

                    if (db.delete(idDelete)) {
                        System.out.println("Xoa thanh cong!");
                    } else {
                        System.out.println("Khong tim thay!");
                    }
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Khong hop le!");
            }
        }
    }
}
