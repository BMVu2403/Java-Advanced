package PTIT_CNTT1_IT203B_Session08.Exercise03;

import java.util.Scanner;

public class SmartHomeApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            RemoteControl remote = new RemoteControl();
            Light light = new Light();
            Fan fan = new Fan();
            AirConditioner ac = new AirConditioner();

            while (true) {
                System.out.println("\n===== REMOTE CONTROL =====");
                System.out.println("1. Gán lệnh cho nút");
                System.out.println("2. Nhấn nút");
                System.out.println("3. Undo");
                System.out.println("4. Thoát");
                System.out.print("Chọn: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Chọn nút (số): ");
                        int slot = scanner.nextInt();

                        System.out.println("1. Bật đèn");
                        System.out.println("2. Tắt đèn");
                        System.out.println("3. Bật quạt");
                        System.out.println("4. Tắt quạt");
                        System.out.println("5. Set điều hòa");
                        System.out.print("Chọn lệnh: ");

                        int cmd = scanner.nextInt();

                        switch (cmd) {
                            case 1:
                                remote.setCommand(slot, new LightOnCommand(light));
                                break;
                            case 2:
                                remote.setCommand(slot, new LightOffCommand(light));
                                break;
                            case 3:
                                remote.setCommand(slot, new FanOnCommand(fan));
                                break;
                            case 4:
                                remote.setCommand(slot, new FanOffCommand(fan));
                                break;
                            case 5:
                                System.out.print("Nhập nhiệt độ: ");
                                int temp = scanner.nextInt();
                                remote.setCommand(slot, new ACSetTemperatureCommand(ac, temp));
                                break;
                        }
                        break;

                    case 2:
                        System.out.print("Nhấn nút: ");
                        remote.pressButton(scanner.nextInt());
                        break;

                    case 3:
                        remote.undo();
                        break;

                    case 4:
                        System.out.println("Thoát chương trình.");
                        return;
                }
            }
        }
    }
}
