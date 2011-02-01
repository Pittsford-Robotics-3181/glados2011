package GLaDOS2011.autono;

import GLaDOS2011.*;

/**
 * Intent: Go for the top-left peg of the right grid.
 * @author Chris Cheng
 */
public class Autono3 {
    // <editor-fold defaultstate="collapsed" desc="public static void Autono3.run()">
    public static void run() {
        if(!EnhancedIO.getDigital(11)){
            AutonoSensors.moveOnLine();
        } else {
            double timerValue = Hardware.gameTimer.get();
            if(timerValue < 1){
                Hardware.drive.driveAtSpeed(.75, .75);
                Claw.roll(true, .5);
            }else if(timerValue < 3){
                Hardware.drive.driveAtSpeed(0.1, -0.1);
                Claw.stop();
            }else if(timerValue < 4){
                Hardware.drive.driveAtSpeed(.75, .75);
            }else if(timerValue < 4.5){
                Hardware.drive.driveAtSpeed(-0.1, 0.1);
                Lifter.goToHeight(9.0);
            }else if(timerValue < 5){
                Lifter.goToHeight(9.0);
            }else if(timerValue < 7){
                Claw.tubeIn_Out(false, .5);
            }else{
                Claw.stop();
            }
        }
    }
    // </editor-fold>
}
