package PTIT_CNTT1_IT203B_Session08.Exercise05;

public class SetACTemperatureCommand implements Command {

    private AirConditioner ac;
    private int temperature;

    public SetACTemperatureCommand(AirConditioner ac, int temperature) {
        this.ac = ac;
        this.temperature = temperature;
    }

    @Override
    public void execute() {
        System.out.println("SleepMode: Điều hòa set " + temperature + "°C");
        ac.setTemperature(temperature);
    }
}
