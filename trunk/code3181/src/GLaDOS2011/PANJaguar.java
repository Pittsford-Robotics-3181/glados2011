package GLaDOS2011;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Wrapper class for Jaguars.
 * @author Ben
 */
public class PANJaguar {
    // The two Jaguars that will be created
    private Jaguar PWMJag;
    private CANJaguar CANJag;
    // The channel and device number
    public int location;
    // Whether or not CAN is working
    public boolean CANEnabled = true;
    // The maximum allowed voltage
    public final double MAX_VOLTAGE = 12.0;
    // The time the Jaguar waits before recovering from a fault
    public final double FAULT_TIME = 0.5;
    //PID constants
    public static final double Kp = .05; // proportional constant
    public static final double Ki = 0; //integral constant
    public static final double Kd = 0; // derivative constant

    /**
     * Constructor that creates the two Jaguars: one PWM, one CAN
     * @param jagNumber The location of the Jaguar(s)
     */
    public PANJaguar(int jagNumber) {
        location = jagNumber;
        PWMJag = new Jaguar(4, jagNumber);
        if(CANEnabled){
            try {
                CANJag = new CANJaguar(jagNumber);
                // Set various CAN values
                CANJag.setPID(Kp, Ki, Kd);
                CANJag.configMaxOutputVoltage(MAX_VOLTAGE);
                CANJag.configFaultTime(FAULT_TIME);
                Hardware.txtout.say(5, "CAN working");
            } catch (CANTimeoutException ex) {
                printError();
            }
        }
    }

    /**
     * Sets the motor(s) to the given value with linear ramping for PWM and
     * built-in PID for CAN.
     * @param value The value to set the motor(s) to
     */
    public void set(double value) {
        PWMJag.set(Hardware.ramping(value, PWMJag.get()));
        if(CANEnabled && CANJag != null){
            try {
                CANJag.setX(value);
            } catch (CANTimeoutException ex) {
                printError();
            }
        }
    }

    /**
     * Gets the current speed
     * @return The current speed
     */
    public double getX() {
        double returnVal = PWMJag.get();
        if(CANEnabled && CANJag != null){
            try {
                returnVal = CANJag.getX();
            } catch (CANTimeoutException ex) {
                printError();
            }
        }
        return returnVal;
    }

    /**
     * Gets the current from the CANJaguar.
     * @return The current, if CAN is working; 0, otherwise
     */
    public double getOutputCurrent() {
        if (CANJag == null){
            return 0;
        }
        try {
            return CANJag.getOutputCurrent();
        } catch (CANTimeoutException ex) {
            printError();
        }
        return 0;
    }

    /**
     * Gets the voltage from the CANJaguar.
     * @return The voltage, if CAN is working; 0, otherwise
     */
    public double getOutputVoltage() {
        if (CANJag == null){
            return 0;
        }
        try {
            return CANJag.getOutputVoltage();
        } catch (CANTimeoutException ex) {
            printError();
        }
        return 0;
    }

    /**
     * Prints an error to the driver station.
     */
    private void printError() {
        Hardware.txtout.say(6, "CAN error on Jag "+location);
    }
}
