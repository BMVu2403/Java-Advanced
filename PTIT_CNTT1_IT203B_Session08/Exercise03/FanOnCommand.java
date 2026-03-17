package PTIT_CNTT1_IT203B_Session08.Exercise03;

public class FanOnCommand implements Command {

    private Fan fan;

    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.on();
    }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        fan.off();
    }
}
