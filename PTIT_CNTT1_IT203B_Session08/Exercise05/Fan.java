package PTIT_CNTT1_IT203B_Session08.Exercise05;

public class Fan implements Observer {

    public void setLow() {
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    public void setHigh() {
        System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
    }

    @Override
    public void update(int temperature) {
        if (temperature > 30) {
            setHigh();
        }
    }
}
