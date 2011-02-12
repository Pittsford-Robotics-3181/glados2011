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
     * Gets a digital input.
     * @param channel Which channel to get
     * @return The returned boolean from the DriverStation
     */
    public static boolean getDigital(int channel) {
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
     * Gets an analog input.
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
     * Sets a digital output.
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
}
