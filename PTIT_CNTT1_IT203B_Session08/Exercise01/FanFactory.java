package PTIT_CNTT1_IT203B_Session08.Exercise01;

public class FanFactory extends DeviceFactory {

    @Override
    public Device createDevice() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Fan();
    }
}
