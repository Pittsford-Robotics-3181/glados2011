package GLaDOS2011.autono;

import GLaDOS2011.*;

/**
 * Intent: Go for the top-middle peg of the left grid.
 * @author Chris Cheng
 */
public class Autono1 {
    public static void run() {
        if(false){
            // Do stuff without dead reckoning
            Autonomous.run(Hanging.TOP+Hanging.CENTER_OFFSET);
        } else {
            // Do stuff with dead reckoning
            double timerValue = Hardware.gameTimer.get();
            if(timerValue < 1){
                Hardware.drive.driveAtSpeed(.75, .75);
                Claw.roll(.5);
            }else if(timerValue < 3){
                Hardware.drive.driveAtSpeed(0, 0);
                Claw.stop();
            }else if(timerValue < 5){
                Claw.tubeInOut(-0.5);
            }else{
                Claw.stop();
            }
        }
    }
}
