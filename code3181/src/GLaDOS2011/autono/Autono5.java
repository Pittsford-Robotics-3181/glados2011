/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GLaDOS2011.autono;

import GLaDOS2011.*;

/**
 *
 * @author Team3181_User
 */
public class Autono5 {
    public static void run() {
        double timerValue = Hardware.gameTimer.get();
        if(timerValue < 2.0){
            Hardware.drive.driveAtSpeed(-0.5, -0.5);
        } else {
            Hardware.drive.driveAtSpeed(0, 0);
        }
    }
}
