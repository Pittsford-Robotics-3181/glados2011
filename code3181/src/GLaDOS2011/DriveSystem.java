package GLaDOS2011;

/**
 * This is the drive system.
 * @author Ben
 */
public class DriveSystem {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    // Are we using linear ramping?
    boolean linear = true;

    //PID variables
    static final double Kp = .05; // proportional constant
    static final double Ki = 0; //integral constant
    static final double Kd = 0; // derivative constant
    // Arrays that contain values for left and right
    double[] previousError = {0, 0};
    double[] integral = {0, 0};
    double[] currentSpeed = {0, 0};
    // Time between calls of periodic functions
    double deltat = .005;

    // Linear ramping
    double lastLeftSpeed = 0.0;
    double lastRightSpeed = 0.0;
    double RAMPING_CONSTANT = .015;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void DriveSystem.driveAtSpeed(double leftSpeed, double rightSpeed)">
    /**
     * This method sets the motor speeds to the given parameters.
     * @param leftSpeed The target left speed
     * @param rightSpeed The target right speed
     */
    public void driveAtSpeed(double leftSpeed, double rightSpeed) {
        // PID doesn't work right now
        if (!linear) {
            Hardware.leftJag.set(PIDOutput(leftSpeed, Hardware.LEFT));
            Hardware.rightJag.set(PIDOutput(rightSpeed, Hardware.RIGHT));
        } else {
            // Left
            double leftDelta = leftSpeed - lastLeftSpeed;
            if (Math.abs(leftDelta) > RAMPING_CONSTANT) {
                leftDelta = ((leftDelta < 0) ? -1 : 1) * RAMPING_CONSTANT;
            }
            lastLeftSpeed += leftDelta;

            // Right
            double rightDelta = rightSpeed - lastRightSpeed;
            if (Math.abs(rightDelta) > RAMPING_CONSTANT) {
                rightDelta = ((rightDelta < 0) ? -1 : 1) * RAMPING_CONSTANT;
            }
            lastRightSpeed += rightDelta;

            // Right motor reversed
            Hardware.leftJag.set(lastLeftSpeed);
            Hardware.rightJag.set(-lastRightSpeed);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public double DriveSystem.PIDOutput(double target, int side)">
    /**
     * This method uses the proportional, integral, derivative controller to
     * ramp the current speed to the target speed.
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
    }
    // </editor-fold>
}
