package PTIT_CNTT1_IT203B_Session08.Exercise02;

public class AirConditioner {

    public void turnOff() {
        System.out.println("FACADE: Tắt điều hòa");
    }

    public void setTemperature(int temp) {
        System.out.println("FACADE: Điều hòa set " + temp + "°C");
    }
}
