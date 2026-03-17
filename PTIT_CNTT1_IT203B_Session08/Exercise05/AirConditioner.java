package PTIT_CNTT1_IT203B_Session08.Exercise05;

public class AirConditioner implements Observer {

    public void setTemperature(int temperature) {
        System.out.println("Điều hòa: Nhiệt độ = " + temperature);
    }

    @Override
    public void update(int temperature) {
        // Giữ nguyên nhiệt độ, có thể mở rộng logic nếu muốn
    }
}
