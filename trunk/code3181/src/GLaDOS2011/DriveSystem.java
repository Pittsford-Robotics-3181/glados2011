package GLaDOS2011;

/**
 * This is the drive system.
 * @author Ben
 */
public class DriveSystem {

    // <editor-fold defaultstate="collapsed" desc="public void DriveSystem.driveAtSpeed(double leftSpeed, double rightSpeed)">
    /**
     * This method sets the motor speeds to the given parameters.
     * @param leftSpeed The target left speed
     * @param rightSpeed The target right speed
     */
    public void driveAtSpeed(double leftSpeed, double rightSpeed) {
        if(Math.floor(Hardware.gameTimer.get())%5==0){
            Hardware.integral[0] = 0;
            Hardware.integral[1] = 0;
        }
        // Right motor is reversed
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
        Hardware.compressor.stop();
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
