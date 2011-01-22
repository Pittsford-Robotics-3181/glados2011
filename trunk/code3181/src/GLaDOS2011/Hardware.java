package GLaDOS2011;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tangible Hardware">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Autonomous Switches">
    public static DigitalInput[] autonoSwitches = {new DigitalInput(7),
                                                   new DigitalInput(8),
                                                   new DigitalInput(9)};
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Joysticks">
    public static Joystick leftJoystick = new Joystick(1);
    public static Joystick rightJoystick = new Joystick(2);
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Intangible Hardware">
    public static DSOutput txtout = new DSOutput();
    public static DriveSystem drive = new DriveSystem();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public static boolean Hardware.checkButton(int button)">
    /**
     * Checks if a specific button is pressed on either joystick
     * @param button The button number to check
     * @return Whether or not one of the buttons is pushed
     */
    public static boolean checkButton(int button) {
        if (leftJoystick.getRawButton(button) || rightJoystick.getRawButton(button)) {
            return true;
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public static boolean Hardware.checkButton(int button, int joystick)">
    /**
     * Checks if a specific button is pressed on one specified joystick
     * @param button The button number to check
     * @return Whether or not one of the buttons is pushed
     */
    public static boolean checkButton(int button, int joystick){
        if(leftJoystick.getRawButton(button) && joystick == LEFT || rightJoystick.getRawButton(button) && joystick == RIGHT)
            return true;
        return false;
    }
    // </editor-fold>
}
