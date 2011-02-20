package GLaDOS2011;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;

/**
 * A way to get input from the Driver Station.
 * @author Ben
 */
public class EnhancedIO extends DriverStation {
    private static DriverStationEnhancedIO IO;
    private static double Ana5;
    private static double Ana3;
    private static double Ana1;

    /**
     * Get a digital input.
     * @param channel Which channel to get
     * @return The returned boolean from the DriverStation
     */
    public static boolean getDigital(int channel) {
        // temporary fix because EnhancedIO doesn't work and we want 11 to return true
        if(channel==11)
            return true;
        // temporary fix because this method causes an exception
        if(1==1)
            return false;
        if((IO = Hardware.driverStation.getEnhancedIO()) == null){
            return false;
        }
        boolean returnVal = false;
        try {
            returnVal = IO.getDigital(channel);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
        return returnVal;
    }

    /**
     * Get an analog input.
     * @param channel Which channel to get
     * @return The returned double from the DriverStation
     */
    public static double getAnalog(int channel) {
        if((IO = Hardware.driverStation.getEnhancedIO()) == null){
            return 0;
        }
        double returnVal = 0;
        try {
            returnVal = IO.getAnalogIn(channel);
        } catch (EnhancedIOException ex) {
//          ex.printStackTrace();
        }
        return returnVal;
    }
    
    /**
     * Set a digital output.
     * @param channel Which channel to set
     * @param value The value to set
     */
    public static void setDigital(int channel, boolean value) {
        if((IO = Hardware.driverStation.getEnhancedIO()) == null){
            return;
        }
        try {
            IO.setDigitalOutput(channel, value);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set an analog output.
     * @param channel Which channel to set
     * @param value The value to set
     */
    public static void setAnalog(int channel, double value) {
        if((IO = Hardware.driverStation.getEnhancedIO()) == null){
            return;
        }
        try {
            IO.setAnalogOut(channel, value);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set an LED to be on or off.
     * @param channel Which channel to set
     * @param value The value to set
     */
    public static void setLED(int channel, boolean value) {
        if((IO = Hardware.driverStation.getEnhancedIO()) == null){
            return;
        }
        try {
            IO.setLED(channel, value);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates the Analog Inputs from the Cypress Box.  This should reduce the amount of overhead by reducing the calls to getAnalog() from 2 per call of getBoxButton() to 3 times a loop.
     */
    public static void updateAnas() {
        Ana1 = getAnalog(1);
        Ana3 = getAnalog(3);
        Ana5 = getAnalog(5);
    }

    /**
     * Get the state of each button or toggle of the Cypress Box.
     * @param button An int from 1 to 17.
     * @return Whether or not that button is pushed or toggle is flipped.  If the int is not in the range, always false.
     */
    public static boolean getBoxButton(int button) {
        if((IO = Hardware.driverStation.getEnhancedIO()) == null){
            return false;
        }
        switch(button){
            case(1):
                if(Ana5 > 0.20 && Ana5 < 0.86) return true;
                else return false;
            case(2):
                if(Ana5 > 0.74 && Ana5 < 1.40) return true;
                else return false;
            case(3):
                if(Ana5 > 1.27 && Ana5 < 1.93) return true;
                else return false;
            case(4):
                if(Ana5 > 1.81 && Ana5 < 2.47) return true;
                else return false;
            case(5):
                if(Ana5 > 2.34 && Ana5 < 3.00) return true;
                else return false;
            case(6):
                if(Ana5 > 2.89 && Ana5 < 3.55) return true;
                else return false;
            case(7):
                if(Ana3 > 0.47 && Ana3 < 1.13) return true;
                else return false;
            case(8):
                if(Ana3 > 1.27 && Ana3 < 1.93) return true;
                else return false;
            case(9):
                if(Ana3 > 2.08 && Ana3 < 2.74) return true;
                else return false;
            case(10):
                if(Ana3 > 2.88 && Ana3 < 3.54) return true;
                else return false;
            case(11):
                return getDigital(8);
            case(12):
                if(Ana1 > 0.72 && Ana1 < 1.38) return true;
                else return false;
            case(13):
                if(Ana1 > 1.77 && Ana1 < 2.43) return true;
                else return false;
            case(14):
                if(Ana1 > 2.83 && Ana1 < 3.49) return true;
                else return false;
            case(15):
                return getDigital(2);
            case(16):
                return getDigital(4);
            case(17):
                return getDigital(6);
            default:
                return false;
        }
    }
}