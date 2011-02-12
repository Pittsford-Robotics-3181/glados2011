package GLaDOS2011;

/**
 * This class contains code that deals with the arm.
 * @author devarakonda yagnavaL
 * @author Eric Lee
 */
public class Arm {
    /**
     * Move elbow in.
     */
    public static void moveElbowIn()
    {
        Hardware.elbowIn.set(true);
        Hardware.elbowOut.set(false);
    }

    /**
     * Move elbow out.
     */
    public static void moveElbowOut()
    {
        Hardware.elbowIn.set(false);
        Hardware.elbowOut.set(true);
    }

    /**
     * Checks if elbow button is on.  If so, it moves the elbow to the out position.
     * Otherwise, it moves the elbow to the in position.
     */
    public static void elbowCheck()
    {
        if(Hardware.elbowSwitch.get())
            moveElbowOut();
        else
            moveElbowIn();
    }
}

