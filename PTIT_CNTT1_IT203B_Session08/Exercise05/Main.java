package PTIT_CNTT1_IT203B_Session08.Exercise05;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        TemperatureSensor sensor = new TemperatureSensor();
        sensor.attach(fan);
        sensor.attach(ac);

        Command offLight = new TurnOffLightCommand(light);
        Command setAC28 = new SetACTemperatureCommand(ac, 28);
        Command fanLow = new SetFanLowCommand(fan);

        Command sleepMode = new SleepModeCommand(
                Arrays.asList(offLight, setAC28, fanLow));

        try (Scanner sc = new Scanner(System.in)) {
            int choice;

            do {
                System.out.println("\n===== SMART HOME =====");
                System.out.println("1. Kích hoạt chế độ ngủ");
                System.out.println("2. Giả lập thay đổi nhiệt độ");
                System.out.println("0. Thoát");
                System.out.print("Chọn: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        sleepMode.execute();
                        break;
                    case 2:
                        System.out.print("Nhập nhiệt độ: ");
                        int temp = sc.nextInt();
                        sensor.setTemperature(temp);
                        break;
                }
            } while (choice != 0);
        }
    }
}
