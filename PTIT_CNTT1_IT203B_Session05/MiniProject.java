package PTIT_CNTT1_IT203B_Session05;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class MiniProject {

    static class Product {
        private int id;
        private String name;
        private double price;
        private int quantity;
        private String category;

        public Product(int id, String name, double price, int quantity, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getCategory() {
            return category;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    static class InvalidProductException extends Exception {
        public InvalidProductException(String message) {
            super(message);
        }
    }

    static class ProductManager {
        private ArrayList<Product> products = new ArrayList<>();

        public void addProduct(Product product) throws InvalidProductException {
            boolean exists = products.stream()
                    .anyMatch(p -> p.getId() == product.getId());

            if (exists) {
                throw new InvalidProductException("ID san pham da ton tai");
            }

            products.add(product);
            System.out.println("Them san pham thanh cong");
        }

        public void displayProducts() {
            if (products.isEmpty()) {
                System.out.println("Danh sach san pham trong");
                return;
            }

            System.out.printf("%-5s %-20s %-10s %-10s %-15s%n",
                    "ID", "Name", "Price", "Quantity", "Category");

            products.forEach(p -> System.out.printf("%-5d %-20s %-10.2f %-10d %-15s%n",
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory()));
        }

        public void updateQuantity(int id, int newQuantity) throws InvalidProductException {
            Optional<Product> productOpt = products.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst();

            if (!productOpt.isPresent()) {
                throw new InvalidProductException("Khong tim thay san pham voi ID: " + id);
            }

            productOpt.get().setQuantity(newQuantity);
            System.out.println("Cap nhat so luong thanh cong");
        }

        public void deleteOutOfStock() {
            products.removeIf(p -> p.getQuantity() == 0);
            System.out.println("Da xoa san pham het hang");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager manager = new ProductManager();
        int choice;

        do {
            System.out.println("\n========= PRODUCT MANAGEMENT SYSTEM =========");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Hien thi danh sach san pham");
            System.out.println("3. Cap nhat so luong theo ID");
            System.out.println("4. Xoa san pham het hang");
            System.out.println("5. Thoat chuong trinh");
            System.out.println("=============================================");
            System.out.print("Lua chon cua ban: ");

            choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.print("Ten san pham: ");
                        String name = scanner.nextLine();

                        System.out.print("Gia: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        System.out.print("So luong: ");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        System.out.print("Danh muc: ");
                        String category = scanner.nextLine();

                        manager.addProduct(new Product(id, name, price, quantity, category));
                        break;

                    case 2:
                        manager.displayProducts();
                        break;

                    case 3:
                        System.out.print("Nhap ID can cap nhat: ");
                        int updateId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Nhap so luong moi: ");
                        int newQuantity = Integer.parseInt(scanner.nextLine());

                        manager.updateQuantity(updateId, newQuantity);
                        break;

                    case 4:
                        manager.deleteOutOfStock();
                        break;

                    case 5:
                        System.out.println("Thoat chuong trinh");
                        break;

                    default:
                        System.out.println("Lua chon khong hop le");
                }
            } catch (InvalidProductException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Loi nhap du lieu");
            }

        } while (choice != 5);

        scanner.close();
    }
}
