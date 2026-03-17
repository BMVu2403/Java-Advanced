package PTIT_CNTT1_IT203B_Session08.Exercise04;

import java.util.Scanner;

public class SmartHomeApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TemperatureSensor sensor = new TemperatureSensor();
            Fan fan = new Fan();
            Humidifier humidifier = new Humidifier();

            while (true) {
                System.out.println("\n===== OBSERVER DEMO =====");
                System.out.println("1. Đăng ký quạt");
                System.out.println("2. Đăng ký máy tạo ẩm");
                System.out.println("3. Thay đổi nhiệt độ");
                System.out.println("4. Thoát");
                System.out.print("Chọn: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        sensor.attach(fan);
                        break;

                    case 2:
                        sensor.attach(humidifier);
                        break;

                    case 3:
                        System.out.print("Nhập nhiệt độ mới: ");
                        int temp = scanner.nextInt();
                        sensor.setTemperature(temp);
                        break;

                    case 4:
                        System.out.println("Thoát chương trình.");
                        return;
                }
            }
        }
    }
}

