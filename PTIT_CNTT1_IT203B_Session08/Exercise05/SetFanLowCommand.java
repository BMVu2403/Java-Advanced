package PTIT_CNTT1_IT203B_Session08.Exercise05;

public class SetFanLowCommand implements Command {

    private Fan fan;

    public SetFanLowCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        System.out.println("SleepMode: Quạt tốc độ thấp");
        fan.setLow();
    }
}
