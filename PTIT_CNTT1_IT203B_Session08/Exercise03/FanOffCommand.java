package PTIT_CNTT1_IT203B_Session08.Exercise03;

public class FanOffCommand implements Command {

    private Fan fan;

    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.off();
    }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        fan.on();
    }
}
