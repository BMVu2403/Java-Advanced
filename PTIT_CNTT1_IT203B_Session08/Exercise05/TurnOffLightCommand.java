package PTIT_CNTT1_IT203B_Session08.Exercise05;

public class TurnOffLightCommand implements Command {

    private Light light;

    public TurnOffLightCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        light.off();
    }
}
