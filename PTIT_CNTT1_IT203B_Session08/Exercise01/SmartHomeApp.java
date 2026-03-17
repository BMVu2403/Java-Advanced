package PTIT_CNTT1_IT203B_Session08.Exercise01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SmartHomeApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            HardwareConnection connection;
            List<Device> devices = new ArrayList<>();

            while (true) {
                System.out.println("\n===== SMART HOME =====");
                System.out.println("1. Kết nối phần cứng");
                System.out.println("2. Tạo thiết bị mới");
                System.out.println("3. Bật thiết bị");
                System.out.println("4. Tắt thiết bị");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        connection = HardwareConnection.getInstance();
                        connection.connect();
                        break;

                    case 2:
                        System.out.println("1. Đèn");
                        System.out.println("2. Quạt");
                        System.out.println("3. Điều hòa");
                        System.out.print("Chọn: ");

                        int type = scanner.nextInt();
                        DeviceFactory factory = null;

                        if (type == 1)
                            factory = new LightFactory();
                        else if (type == 2)
                            factory = new FanFactory();
                        else if (type == 3)
                            factory = new AirConditionerFactory();

                        if (factory != null) {
                            devices.add(factory.createDevice());
                        }
                        break;

                    case 3:
                        if (devices.isEmpty()) {
                            System.out.println("Chưa có thiết bị.");
                            break;
                        }
                        System.out.print("Chọn thiết bị: ");
                        devices.get(scanner.nextInt() - 1).turnOn();
                        break;

                    case 4:
                        if (devices.isEmpty()) {
                            System.out.println("Chưa có thiết bị.");
                            break;
                        }
                        System.out.print("Chọn thiết bị: ");
                        devices.get(scanner.nextInt() - 1).turnOff();
                        break;

                    case 5:
                        System.out.println("Thoát chương trình.");
                        return;
                }
            }
        }
    }
}
