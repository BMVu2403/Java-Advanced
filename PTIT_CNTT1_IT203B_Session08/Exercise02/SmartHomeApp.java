package PTIT_CNTT1_IT203B_Session08.Exercise02;

import java.util.Scanner;

public class SmartHomeApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            OldThermometer oldThermometer = new OldThermometer();
            TemperatureSensor adapter = new ThermometerAdapter(oldThermometer);
            SmartHomeFacade facade = new SmartHomeFacade(adapter);

            while (true) {
                System.out.println("\n===== SMART HOME =====");
                System.out.println("1. Xem nhiệt độ hiện tại");
                System.out.println("2. Chế độ rời nhà");
                System.out.println("3. Chế độ ngủ");
                System.out.println("4. Thoát");
                System.out.print("Chọn: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        double temp = facade.getCurrentTemperature();
                        System.out.printf(
                                "Nhiệt độ hiện tại: %.1f°C (chuyển đổi từ 78°F)%n",
                                temp);
                        break;

                    case 2:
                        facade.leaveHome();
                        break;

                    case 3:
                        facade.sleepMode();
                        break;

                    case 4:
                        System.out.println("Thoát chương trình.");
                        return;
                }
            }
        }
    }
}
