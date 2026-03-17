package PTIT_CNTT1_IT203B_Session08.Exercise01;

public class AirConditionerFactory extends DeviceFactory {

    @Override
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new AirConditioner();
    }
}
