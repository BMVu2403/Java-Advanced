package PTIT_CNTT1_IT203B_Session08.Exercise03;

public class ACSetTemperatureCommand implements Command {

    private AirConditioner ac;
    private int newTemp;
    private int oldTemp;

    public ACSetTemperatureCommand(AirConditioner ac, int newTemp) {
        this.ac = ac;
        this.newTemp = newTemp;
    }

    @Override
    public void execute() {
        oldTemp = ac.getTemperature();
        ac.setTemperature(newTemp);
    }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        ac.setTemperature(oldTemp);
    }
}
