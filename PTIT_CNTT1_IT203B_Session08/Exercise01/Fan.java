package PTIT_CNTT1_IT203B_Session08.Exercise01;

public class Fan implements Device {

    @Override
    public void turnOn() {
        System.out.println("Quạt: Bắt đầu quay.");
    }

    @Override
    public void turnOff() {
        System.out.println("Quạt: Dừng quay.");
    }
}
