package PTIT_CNTT1_IT203B_Session09.MiniProject;

import java.util.Scanner;

public class ProductFactory {

    public static Product createProduct(int type, Scanner sc) {
        System.out.print("Nhap ID: ");
        String id = sc.nextLine();

        System.out.print("Nhap Name: ");
        String name = sc.nextLine();

        double price = inputDouble(sc, "Nhap Price: ");

        if (type == 1) {
            double weight = inputDouble(sc, "Nhap Weight: ");
            return new PhysicalProduct(id, name, price, weight);
        } else if (type == 2) {
            double size = inputDouble(sc, "Nhap Size (MB): ");
            return new DigitalProduct(id, name, price, size);
        }
        return null;
    }

    private static double inputDouble(Scanner sc, String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Nhap sai dinh dang!");
            }
        }
    }
}
