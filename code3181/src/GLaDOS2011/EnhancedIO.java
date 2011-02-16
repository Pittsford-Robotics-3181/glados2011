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
            ex.printStackTrace();
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
}