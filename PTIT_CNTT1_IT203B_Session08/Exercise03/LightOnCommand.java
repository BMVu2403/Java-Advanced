package PTIT_CNTT1_IT203B_Session08.Exercise03;

public class LightOnCommand implements Command {

    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        light.off();
    }
}
