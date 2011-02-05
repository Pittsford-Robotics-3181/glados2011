package GLaDOS2011;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This is the hardware wrapper for our bot. Everything is static, allowing for
 * easy access.
 * @author Chris Cheng
 * @author Ben
 * @author Eric Lee
 * @see DSOutput
 */
public class Hardware {

    // Constants
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final double RAMPING_CONSTANT = .015;
    static final double MAX_CURRENT = 40.0;

    // Other primitive
    public static int gearMode = 1;

    // Pseudohardware
    public static DSOutput txtout = new DSOutput();
    public static DriveSystem drive = new DriveSystem();
    public static DriverStation driverStation;
    public static Timer gameTimer = new Timer();

    // Digital inputs
    public static DigitalInput leftSensor = new DigitalInput(1);
    public static DigitalInput centerSensor = new DigitalInput(2);
    public static DigitalInput rightSensor = new DigitalInput(3);

    // Solenoids
    public static Solenoid leftGearShift = new Solenoid(3);
    public static Solenoid rightGearShift = new Solenoid(4);
    public static Solenoid elbowOut = new Solenoid(6);
    public static Solenoid elbowIn = new Solenoid(7);

    // Motors
    public static PANJaguar leftMotorI = new PANJaguar(6);
    public static PANJaguar leftMotorII = new PANJaguar(7);
    public static PANJaguar rightMotorI = new PANJaguar(5);
    public static PANJaguar rightMotorII = new PANJaguar(2);
    public static PANJaguar arm = new PANJaguar(3);
    public static PANJaguar lifter = new PANJaguar(4);

    public static Victor topClaw = new Victor(4,9);
    public static Victor bottomClaw = new Victor(4,8);

    // Autonomous switches (may be changed in future to EnhancedIO)
    public static DigitalInput[] autonoSwitches = {new DigitalInput(7),
                                                   new DigitalInput(8),
                                                   new DigitalInput(9)};

    // Limit switch for elbow
    public static DigitalInput elbowSwitch = new DigitalInput(10);

    //Joystick
    public static Joystick leftJoystick = new Joystick(1);
    public static Joystick rightJoystick = new Joystick(2);

    //Compressor
    public static Compressor compressor = new Compressor(14, 1);


    //------------$*$*$*$*$*$*$*$*METHODS*$*$*$*$*$*$*$*$------------//

    /**
     * Checks if a specific button is pressed on either joystick
     * @param button The button number to check
     * @return Whether or not one of the buttons is pushed
     */
    public static boolean checkButton(int button) {
        if (leftJoystick.getRawButton(button) || rightJoystick.getRawButton(button))
            return true;
        return false;
    }

    /**
     * Checks if a specific button is pressed on one specified joystick
     * @param button The button number to check
     * @param joystick Which joystick to check
     * @return Whether or not one of the buttons is pushed
     */
    public static boolean checkButton(int button, int joystick){
        if(leftJoystick.getRawButton(button) && joystick == LEFT || rightJoystick.getRawButton(button) && joystick == RIGHT)
            return true;
        return false;
    }

    /**
     * Ramps a given speed toward a target speed.
     * @param targetSpeed The target speed
     * @param currentSpeed The current speed
     * @return The speed to be set
     */
    public static double ramping(double targetSpeed, double currentSpeed) {
        double delta = targetSpeed - currentSpeed;
        if (Math.abs(delta) > RAMPING_CONSTANT) {
            delta = ((delta < 0) ? -1 : 1) * RAMPING_CONSTANT;
        }
        currentSpeed += delta;
        return currentSpeed;
    }

    /**
     * Ramps a given speed toward a target speed with specified maximum gain.
     * @param targetSpeed The target speed
     * @param currentSpeed The current speed
     * @param maxGain The maximum allowed gain
     * @return The speed to be set
     */
    public static double ramping(double targetSpeed, double currentSpeed, double maxGain) {
        double delta = targetSpeed - currentSpeed;
        if (Math.abs(delta) > maxGain) {
            delta = ((delta < 0) ? -1 : 1) * maxGain;
        }
        currentSpeed += delta;
        return currentSpeed;
    }

    /**
     * Shifts the gear. We need more info on how the solenoids shift the gear.
     * This probably won't work, but it's a start.
     * @param mode Which gear to shift to
     */
    public static void shiftGear(int mode) {
        boolean high = (mode == 1);
        leftGearShift.set(high);
        rightGearShift.set(high);
        gearMode = mode;
    }

    /**
     * See if one of the drive motors is drawing too much current.
     * @return Whether there is a current spike
     */
    public static boolean checkCurrentSpike() {
        return leftMotorI.getOutputCurrent() > MAX_CURRENT ||
               leftMotorII.getOutputCurrent() > MAX_CURRENT ||
               rightMotorI.getOutputCurrent() > MAX_CURRENT ||
               rightMotorII.getOutputCurrent() > MAX_CURRENT;
    }

    //------------$*$*$*$*$*$*$*$*PID CONTROL*$*$*$*$*$*$*$*$------------//

    //PID constants
    public static final double Kp = .05; // proportional constant
    public static final double Ki = 0; //integral constant
    public static final double Kd = 0; // derivative constant

    // Arrays that contain values for left and right
    static double[] previousError = {0, 0};
    static double[] integral = {0, 0};
    static double[] currentSpeed = {0, 0};

    // Time between calls of periodic functions
    static double deltat = .005;
    /**
     * This method uses the proportional, integral, derivative controller to
     * ramp the current speed to the target speed.
     * THIS METHOD IS NOT BEING USED RIGHT NOW. If you want to use this, REMOVE THIS LINE!
     * @param target The target speed
     * @param side Which side the PID is affecting
     * @return The ramped speed
     */
    public static double PIDOutput(double target, int side) {
        double error = target - currentSpeed[side];
        integral[side] = integral[side] + error * deltat;
        double derivative = (error - previousError[side]) / deltat;
        double output = Kp * error + Ki * integral[side] + Kd * derivative;
        previousError[side] = error;
        return currentSpeed[side] + output;
    }
}
