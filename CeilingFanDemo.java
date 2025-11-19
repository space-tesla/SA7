import java.util.Scanner;

// Receiver
class CeilingFan {
    int speed = 0; // 0=Off, 1=Medium, 2=High

    void high()   { speed = 2; System.out.println("Fan on HIGH"); }
    void medium() { speed = 1; System.out.println("Fan on MEDIUM"); }
    void off()    { speed = 0; System.out.println("Fan is OFF"); }

    int getSpeed() { return speed; }
}

// Command Interface
interface Command {
    void execute();
    void undo();
}

// Concrete Commands
class HighCommand implements Command {
    CeilingFan fan; int prev;
    HighCommand(CeilingFan f){ fan=f;}
    public void execute(){ prev=fan.getSpeed(); fan.high(); }
    public void undo(){ restore(prev); }

    void restore(int s){
        if(s==2) fan.high();
        else if(s==1) fan.medium();
        else fan.off();
    }
}

class MediumCommand implements Command {
    CeilingFan fan; int prev;
    MediumCommand(CeilingFan f){ fan=f;}
    public void execute(){ prev=fan.getSpeed(); fan.medium(); }
    public void undo(){ restore(prev); }

    void restore(int s){
        if(s==2) fan.high();
        else if(s==1) fan.medium();
        else fan.off();
    }
}

class OffCommand implements Command {
    CeilingFan fan; int prev;
    OffCommand(CeilingFan f){ fan=f;}
    public void execute(){ prev=fan.getSpeed(); fan.off(); }
    public void undo(){ restore(prev); }

    void restore(int s){
        if(s==2) fan.high();
        else if(s==1) fan.medium();
        else fan.off();
    }
}

// Invoker
class Remote {
    Command last;
    void setCommand(Command c){ last=c; }
    void press(){ last.execute(); }
    void undo(){ 
        if(last!=null){ 
            System.out.println("Undoing..."); 
            last.undo(); 
        }
    }
}

public class CeilingFanDemo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CeilingFan fan = new CeilingFan();
        Remote remote = new Remote();

        Command high = new HighCommand(fan);
        Command med  = new MediumCommand(fan);
        Command off  = new OffCommand(fan);

        int ch;
        do {
            System.out.println("\n1.High  2.Medium  3.Off  4.Undo  5.Exit");
            System.out.print("Choice: ");
            ch = sc.nextInt();

            if (ch == 1) { remote.setCommand(high); remote.press(); }
            else if (ch == 2) { remote.setCommand(med); remote.press(); }
            else if (ch == 3) { remote.setCommand(off); remote.press(); }
            else if (ch == 4) { remote.undo(); }

        } while (ch != 5);

        sc.close();
    }
}
