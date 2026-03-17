package PTIT_CNTT1_IT203B_Session08.Exercise01;

public class LightFactory extends DeviceFactory {

    @Override
    public Device createDevice() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Light();
    }
}
