package GLaDOS2011.autono;

import GLaDOS2011.*;

/**
 * Intent: Go for the top-right peg of the left grid.
 * @author Chris Cheng
 */
public class Autono2 {
    public static void run() {
        if(!EnhancedIO.getDigital(11)){
            // Do stuff without dead reckoning
            if(Sensors.atEnd){
                if(Lifter.closeEnough(9.0)){
                    Lifter.stop();
                    Claw.tubeIn_Out(-.5);
                } else {
                    Lifter.goToHeight(9.0);
                }
            } else {
                Sensors.moveOnLine();
            }
        } else {
            // Do stuff with dead reckoning
            double timerValue = Hardware.gameTimer.get();
            if(timerValue < 1){
                Hardware.drive.driveAtSpeed(.75, .75);
                Claw.roll(.5);
            }else if(timerValue < 3){
                Hardware.drive.driveAtSpeed(-0.1, 0.1);
                Claw.stop();
            }else if(timerValue < 4){
                Hardware.drive.driveAtSpeed(.75, .75);
            }else if(timerValue < 4.5){
                Hardware.drive.driveAtSpeed(0.1, -0.1);
                Lifter.goToHeight(9.0);
            }else if(timerValue < 5){
                Lifter.goToHeight(9.0);
            }else if(timerValue < 7){
                Claw.tubeIn_Out(-0.5);
            }else{
                Claw.stop();
            }
        }
    }
}
