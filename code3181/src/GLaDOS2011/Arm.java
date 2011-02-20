package GLaDOS2011;

import edu.wpi.first.wpilibj.Timer;

/**
 * This class contains code that deals with the arm.
 * @author devarakonda yagnavaL
 * @author Eric Lee
 */
public class Arm {
    public static Timer extendTimer = new Timer();

    /**
     * Move elbow in.
     */
    public static void moveElbowIn() {
        Hardware.elbowIn.set(true);
        Hardware.elbowOut.set(false);
    }

    /**
     * Move elbow out.
     */
    public static void moveElbowOut() {
        Hardware.elbowIn.set(false);
        Hardware.elbowOut.set(true);
    }

    /**
     * Don't let the arm move.
     */
    public static void stopArm(){
        Hardware.elbowIn.set(false);
        Hardware.elbowOut.set(false);
    }

    /**
     * Checks if elbow button is on.  If so, it moves the elbow to the out position.
     * Otherwise, it moves the elbow to the in position.
     */
    public static void control() {
        if(EnhancedIO.getBoxButton(15)){
            extendTimer.start();
            if(extendTimer.get() < 1.0 || Lifter.getHeight() < 2.0){
                moveElbowOut();
            } else {
                stopArm();
            }
        } else {
            extendTimer.stop();
            extendTimer.reset();
            moveElbowIn();
        }
    }
}

