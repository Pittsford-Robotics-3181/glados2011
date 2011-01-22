package GLaDOS2011;

import GLaDOS2011.Hardware;

/**
 * This class contains code that deals with the arm.
 * @author devarakonda yagnavaL
 */
public class Arm {
    /**
     * Objective of method = Arm movement based on motor's status quo
     * Up if positive, down if negative; Motor movement controls arm movement
     * Motor moves forward = Arm goes up; Motor moves backward = Arm goes down
     * @param speed Double ranging from -1.0 to 1.0
     *
     */
    // <editor-fold defaultstate="collapsed" desc="public void setSpeed(double speed)">
    public void setSpeed(double speed) {
        Hardware.arm.set(speed);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="public void setMovement(boolean up)">
    public void setMovement(boolean up) {
        if (up) {
            Hardware.arm.set(1.0);

        } else {
            Hardware.arm.set(-1.0);

        }
    }
    // </editor-fold>
}

