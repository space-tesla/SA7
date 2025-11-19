// Command Interface with Undo
interface Command {
    void execute();
    void undo();
}

// Receiver: Ceiling Fan
class CeilingFan {
    public static final int HIGH = 3;
    public static final int LOW = 1;
    public static final int OFF = 0;

    private int speed;

    public CeilingFan() {
        speed = OFF;
    }

    public void high() {
        speed = HIGH;
        System.out.println("Ceiling Fan set to HIGH");
    }

    public void low() {
        speed = LOW;
        System.out.println("Ceiling Fan set to LOW");
    }

    public void off() {
        speed = OFF;
        System.out.println("Ceiling Fan is OFF");
    }

    public int getSpeed() {
        return speed;
    }
}

// Concrete Command: HIGH
class CeilingFanHighCommand implements Command {
    CeilingFan fan;
    int prevSpeed;

    public CeilingFanHighCommand(CeilingFan fan) {
        this.fan = fan;
    }

    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.high();
    }

    public void undo() {
        if (prevSpeed == CeilingFan.LOW) fan.low();
        else if (prevSpeed == CeilingFan.OFF) fan.off();
    }
}

// Concrete Command: LOW
class CeilingFanLowCommand implements Command {
    CeilingFan fan;
    int prevSpeed;

    public CeilingFanLowCommand(CeilingFan fan) {
        this.fan = fan;
    }

    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.low();
    }

    public void undo() {
        if (prevSpeed == CeilingFan.HIGH) fan.high();
        else if (prevSpeed == CeilingFan.OFF) fan.off();
    }
}

// Concrete Command: OFF
class CeilingFanOffCommand implements Command {
    CeilingFan fan;
    int prevSpeed;

    public CeilingFanOffCommand(CeilingFan fan) {
        this.fan = fan;
    }

    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.off();
    }

    public void undo() {
        if (prevSpeed == CeilingFan.HIGH) fan.high();
        else if (prevSpeed == CeilingFan.LOW) fan.low();
    }
}

// Invoker: Remote Control
class RemoteControl {
    private Command slot;
    private Command lastCommand;

    public void setCommand(Command command) {
        slot = command;
    }

    public void pressButton() {
        slot.execute();
        lastCommand = slot;
    }

    public void pressUndo() {
        System.out.println("Undoing last action...");
        lastCommand.undo();
    }
}

// Demo Class
public class CeilingFanDemo {
    public static void main(String[] args) {

        CeilingFan fan = new CeilingFan();

        Command highCmd = new CeilingFanHighCommand(fan);
        Command lowCmd = new CeilingFanLowCommand(fan);
        Command offCmd = new CeilingFanOffCommand(fan);

        RemoteControl remote = new RemoteControl();

        System.out.println("---- Testing Ceiling Fan Commands ----");

        remote.setCommand(highCmd);
        remote.pressButton();
        remote.pressUndo();

        remote.setCommand(lowCmd);
        remote.pressButton();
        remote.pressUndo();

        remote.setCommand(offCmd);
        remote.pressButton();
        remote.pressUndo();
    }
}
