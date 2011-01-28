package GLaDOS2011;

/**
 * This is the drive system.
 * @author Ben
 */
public class DriveSystem {

    // <editor-fold defaultstate="collapsed" desc="Variables">

    //PID constants
    public static final double Kp = .05; // proportional constant
    public static final double Ki = 0; //integral constant
    public static final double Kd = 0; // derivative constant

    // Arrays that contain values for left and right
    double[] previousError = {0, 0};
    double[] integral = {0, 0};
    double[] currentSpeed = {0, 0};
    
    // Time between calls of periodic functions
    double deltat = .005;

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
            integral[0] = 0;
            integral[1] = 0;
        }
        Hardware.leftJag.set(lastLeftSpeed);
        Hardware.rightJag.set(-lastRightSpeed);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public double DriveSystem.PIDOutput(double target, int side)">
    /**
     * This method uses the proportional, integral, derivative controller to
     * ramp the current speed to the target speed.
     * THIS METHOD IS NOT BEING USED RIGHT NOW.
     * @param target The target speed
     * @param side Which side the PID is affecting
     * @return The ramped speed
     */
    public double PIDOutput(double target, int side) {
        double error = target - currentSpeed[side];
        integral[side] = integral[side] + error * deltat;
        double derivative = (error - previousError[side]) / deltat;
        double output = Kp * error + Ki * integral[side] + Kd * derivative;
        previousError[side] = error;
        return currentSpeed[side] + output;
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
        return Math.floor(Hardware.leftJag.getX() * 128) + "," + Math.floor(Hardware.rightJag.getX() * 128);
    }
}
