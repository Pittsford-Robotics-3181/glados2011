package GLaDOS2011;

import GLaDOS2011.util.*;

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
        // Reset PID integral part
        if(Math.floor(Hardware.gameTimer.get())%5==0){
            Hardware.integral[0] = 0;
            Hardware.integral[1] = 0;
        }

        // Make speeds zero if they are too small
        leftSpeed = Utils.checkForSmall(leftSpeed);
        rightSpeed = Utils.checkForSmall(rightSpeed);

        // Right motor is reversed
        Hardware.leftMotorI.set(leftSpeed);
        Hardware.leftMotorII.set(leftSpeed);
        Hardware.rightMotorI.set(-rightSpeed);
        Hardware.rightMotorII.set(-rightSpeed);
    }

    /**
     * Stops the robot.
     */
    public void stop() {
        Hardware.leftMotorI.set(0);
        Hardware.leftMotorII.set(0);
        Hardware.rightMotorI.set(0);
        Hardware.rightMotorII.set(0);
        Hardware.compressor.stop();
    }

    /**
     * Overrides default toString.
     * @return The left and right motor speeds separated by a comma
     */
    public String toString() {
        return (int) Math.floor(Hardware.leftMotorI.getX() * 128) + "," + (int) Math.floor(Hardware.rightMotorI.getX() * 128);
    }
}
