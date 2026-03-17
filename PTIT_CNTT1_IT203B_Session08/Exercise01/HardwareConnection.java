package PTIT_CNTT1_IT203B_Session08.Exercise01;

public class HardwareConnection {

    private static HardwareConnection instance;
    private boolean connected = false;

    private HardwareConnection() {
    }

    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
        }
        return instance;
    }

    public void connect() {
        if (!connected) {
            System.out.println("HardwareConnection: Đã kết nối phần cứng.");
            connected = true;
        }
    }

    public void disconnect() {
        if (connected) {
            System.out.println("HardwareConnection: Đã ngắt kết nối.");
            connected = false;
        }
    }
}
