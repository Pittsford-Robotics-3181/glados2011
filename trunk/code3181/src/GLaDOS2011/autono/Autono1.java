package GLaDOS2011.autono;

import GLaDOS2011.*;

/**
 * Intent: Go for the top-middle peg of the left grid.
 * @author Chris Cheng
 */
public class Autono1 {
    public static void run() {
        if(!EnhancedIO.getDigital(11)){
            AutonoSensors.moveOnLine();
        } else {
            double timerValue = Hardware.gameTimer.get();
            if(timerValue < 1){
                Hardware.drive.driveAtSpeed(.75, .75);
                Claw.roll(.5);
            }else if(timerValue < 3){
                Hardware.drive.driveAtSpeed(0, 0);
                Lifter.goToHeight(9.0);
                Claw.stop();
            }else if(timerValue < 5){
                Claw.tubeIn_Out(-0.5);
            }else{
                Claw.stop();
            }
        }
    }
}