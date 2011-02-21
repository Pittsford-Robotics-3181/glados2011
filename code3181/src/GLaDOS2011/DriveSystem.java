package GLaDOS2011;

/**
 * This is the drive system.
 * @author Ben
 */
public class DriveSystem {
    /**
     * This method sets the motor speeds to the given parameters.
     * @param leftSpeed The target left speed
     * @param rightSpeed The target right speed
     */
    public void driveAtSpeed(double leftSpeed, double rightSpeed) {
        // Right motor is reversed
        Hardware.leftDrive.set(leftSpeed);
        Hardware.rightDrive.set(-rightSpeed);
        Hardware.leftMotorI.set(leftSpeed);
        Hardware.leftMotorII.set(leftSpeed);
        Hardware.rightMotorI.set(-rightSpeed);
        Hardware.rightMotorII.set(-rightSpeed);

    }

    /**
     * Shifts the gear.
     * We aren't sure which solenoids should do which actions, but they are easy
     * to switch, so no worries.
     * @param mode Which gear to shift to
     */
    public void shiftGear(int mode) {
        boolean high = (mode == Hardware.HIGH);
        Hardware.gearShiftOpen.set(high);
        Hardware.gearShiftClose.set(!high);
        Hardware.gearMode = mode;
    }

    /**
     * Stops the robot.
     */
    public void stop() {
        Hardware.leftDrive.set(0);
        Hardware.rightDrive.set(0);
        Hardware.leftMotorI.set(0);
        Hardware.leftMotorII.set(0);
        Hardware.rightMotorI.set(0);
        Hardware.rightMotorII.set(0);
    }
}
