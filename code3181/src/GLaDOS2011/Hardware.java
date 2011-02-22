package GLaDOS2011;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;

/**
 * This is the hardware wrapper for our bot. Everything is static, allowing for
 * easy access.
 * @author Chris Cheng
 * @author Ben
 * @author Eric Lee
 * @see DSOutput
 */
public class Hardware {

    //------------$*$*$*$*$*$*$*$*VARIABLES*$*$*$*$*$*$*$*$------------//

    // Constants
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static int LOW = 0;
    static int HIGH = 1;
    static final double RAMPING_CONSTANT = .75;
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

    // Analog input
    public static AnalogChannel heightSensor = new AnalogChannel(4);

    // Solenoids
    public static Solenoid gearShiftOpen = new Solenoid(3);
    public static Solenoid gearShiftClose = new Solenoid(4);
    public static Solenoid elbowOut = new Solenoid(5);
    public static Solenoid elbowIn = new Solenoid(7);
    public static Solenoid minibot = new Solenoid(8);

    // Relays
    public static Relay liftBreak = new Relay(6);

    // Motors

    public static Jaguar lifter = new Jaguar(10);
    public static Victor topClaw = new Victor(4,9);
    public static Victor bottomClaw = new Victor(4,8);
    
    public static Victor leftDrive = new Victor(4,1);
    public static Jaguar rightDrive = new Jaguar(4,3);


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
    public static Compressor compressor = new Compressor(14, 2);


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
     * *** Not Used as of 21-Feb-2011 ***
     * @param targetSpeed The target speed
     * @param currentSpeed The current speed
     * @return The speed to be set
     */
    public static double xramping(double targetSpeed, double currentSpeed) {
        double delta = targetSpeed - currentSpeed;
        if (Math.abs(delta) > RAMPING_CONSTANT) {
            delta = ((delta < 0) ? -1 : 1) * RAMPING_CONSTANT;
        }
        currentSpeed += delta;
        return currentSpeed;
    }

    /**
     * Ramps a given speed toward a target speed with specified maximum gain.
     * *** NOT USED as of 21-Feb-2011
     * @param targetSpeed The target speed
     * @param currentSpeed The current speed
     * @param maxGain The maximum allowed gain
     * @return The speed to be set
     */
    public static double xramping(double targetSpeed, double currentSpeed, double maxGain) {
        double delta = targetSpeed - currentSpeed;
        if (Math.abs(delta) > maxGain) {
            delta = ((delta < 0) ? -1 : 1) * maxGain;
        }
        currentSpeed += delta;
        return currentSpeed;
    }

}
