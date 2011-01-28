package GLaDOS2011;

/**
 * This is the drive system.
 * @author Ben
 */
public class DriveSystem {

    // <editor-fold defaultstate="collapsed" desc="Variables">

    // Placeholder speed values
    double lastLeftSpeed = 0.0;
    double lastRightSpeed = 0.0;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void DriveSystem.driveAtSpeed(double leftSpeed, double rightSpeed)">
    /**
     * This method sets the motor speeds to the given parameters.
     * @param leftSpeed The target left speed
     * @param rightSpeed The target right speed
     */
    public void driveAtSpeed(double leftSpeed, double rightSpeed) {
        // Right motor is reversed
        if(Math.floor(Hardware.gameTimer.get())%5==0){
            Hardware.integral[0] = 0;
            Hardware.integral[1] = 0;
        }
        Hardware.leftJag.set(leftSpeed);
        Hardware.rightJag.set(-rightSpeed);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void DriveSystem.stop()">
    /**
     * Stops the robot.
     */
    public void stop() {
        Hardware.leftJag.set(0);
        Hardware.rightJag.set(0);
        lastLeftSpeed = 0;
        lastRightSpeed = 0;
    }
    // </editor-fold>

    /**
     * Overrides default toString.
     * @return The left and right motor speeds separated by a comma
     */
    public String toString() {
        return (int) Math.floor(Hardware.leftJag.getX() * 128) + "," + (int) Math.floor(Hardware.rightJag.getX() * 128);
    }
}
