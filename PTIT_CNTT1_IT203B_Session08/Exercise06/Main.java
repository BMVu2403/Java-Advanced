package PTIT_CNTT1_IT203B_Session08.Exercise06;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Chọn kênh bán hàng:");
            System.out.println("1. Website");
            System.out.println("2. Mobile App");
            System.out.println("3. POS");

            int choice = sc.nextInt();
            sc.nextLine();

            SalesChannelFactory factory;

            switch (choice) {
                case 1:
                    factory = new WebsiteFactory();
                    System.out.println("Bạn đã chọn kênh Website");
                    break;
                case 2:
                    factory = new MobileAppFactory();
                    System.out.println("Bạn đã chọn kênh Mobile App");
                    break;
                case 3:
                    factory = new POSFactory();
                    System.out.println("Bạn đã chọn kênh POS");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    return;
            }

            Order order = new Order();
            System.out.print("Nhập tên sản phẩm: ");
            order.productName = sc.nextLine();
            System.out.print("Nhập giá: ");
            order.price = sc.nextDouble();
            System.out.print("Nhập số lượng: ");
            order.quantity = sc.nextInt();

            OrderService service = new OrderService(factory);
            service.processOrder(order);
        }
    }
}
