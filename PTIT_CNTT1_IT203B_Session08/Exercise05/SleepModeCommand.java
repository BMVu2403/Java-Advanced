package PTIT_CNTT1_IT203B_Session08.Exercise05;

import java.util.List;

public class SleepModeCommand implements Command {

    private List<Command> commands;

    public SleepModeCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for (Command c : commands) {
            c.execute();
        }
    }
}
