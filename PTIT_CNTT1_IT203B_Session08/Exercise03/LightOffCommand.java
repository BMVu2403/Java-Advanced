package PTIT_CNTT1_IT203B_Session08.Exercise03;

public class LightOffCommand implements Command {

    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        light.on();
    }
}
