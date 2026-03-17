package PTIT_CNTT1_IT203B_Session08.Exercise01;

public class AirConditioner implements Device {

    @Override
    public void turnOn() {
        System.out.println("Điều hòa: Làm mát.");
    }

    @Override
    public void turnOff() {
        System.out.println("Điều hòa: Tắt.");
    }
}
