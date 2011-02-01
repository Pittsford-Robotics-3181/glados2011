package GLaDOS2011;

/**
 * This class contains code that deals with the arm.
 * @author devarakonda yagnavaL
 * @author Eric Lee
 */
public class Arm {
    /**
     * Objective of method = Arm movement based on motor's status quo
     * Up if positive, down if negative; Motor movement controls arm movement
     * Motor moves forward = Arm goes up; Motor moves backward = Arm goes down
     * @param speed Double ranging from -1.0 to 1.0
     *
     */
    public void setSpeed(double speed) {
        Hardware.arm.set(speed);
    }

    public void setMovement(boolean up) {
        if (up) {
            Hardware.arm.set(1.0);

        } else {
            Hardware.arm.set(-1.0);

        }
    }

    /**
     * Moves Elbow in.
     */
    public static void moveElbowIn()
    {
        Hardware.elbowIn.set(true);
        Hardware.elbowOut.set(false);
    }

    /**
     * Moves Elbow Out
     */
    public static void moveElbowOut()
    {
        Hardware.elbowIn.set(false);
        Hardware.elbowOut.set(true);
    }

    /**
     * Checks if elbow button is on.  If so it moves the elbow to the out position.
     * else it moves the elbow to the in position.
     */
    public static void elbowCheck()
    {
        if(Hardware.elbowSwitch.get())
            moveElbowOut();
        else
            moveElbowIn();
    }
}

