package GLaDOS2011;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This is the hardware wrapper for our bot.
 * @author Chris Cheng
 * @author Ben
 * @author Eric Lee
 * @see DSOutput
 */
public class Hardware {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final double RAMPING_CONSTANT = .015;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tangible Hardware">
    public static Solenoid elbowOut = new Solenoid(6);
    public static Solenoid elbowIn = new Solenoid(7);
    // <editor-fold defaultstate="collapsed" desc="Sensors">
    public static DigitalInput leftSensor = new DigitalInput(1);
    public static DigitalInput centerSensor = new DigitalInput(2);
    public static DigitalInput rightSensor = new DigitalInput(3);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Motors">
    public static PANJaguar leftJag = new PANJaguar(10);
    public static PANJaguar rightJag = new PANJaguar(2);
    public static PANJaguar arm = new PANJaguar(4);
    public static PANJaguar lifter = new PANJaguar(5);

    public static Victor topClaw = new Victor(4,7);
    public static Victor bottomClaw = new Victor(4,8);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Autonomous Switches">
    public static DigitalInput[] autonoSwitches = {new DigitalInput(7),
                                                   new DigitalInput(8),
                                                   new DigitalInput(9)};
    public static DigitalInput elbowSwitch = new DigitalInput(10);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Joysticks">
    public static Joystick leftJoystick = new Joystick(1);
    public static Joystick rightJoystick = new Joystick(2);
    // </editor-fold>

    public static Compressor compressor = new Compressor(14, 1);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Intangible Hardware">
    public static DSOutput txtout = new DSOutput();
    public static DriveSystem drive = new DriveSystem();
    public static DriverStation driverStation;
    public static Timer gameTimer = new Timer();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public static boolean Hardware.checkButton(int button)">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public static boolean Hardware.checkButton(int button, int joystick)">
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
    // </editor-fold>

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

    // <editor-fold defaultstate="collapsed" desc="public double Hardware.PIDOutput(double target, int side)">

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
     * THIS METHOD IS NOT BEING USED RIGHT NOW.
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
    // </editor-fold>
}
