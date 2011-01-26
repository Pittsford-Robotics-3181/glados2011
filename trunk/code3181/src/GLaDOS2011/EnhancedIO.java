package GLaDOS2011;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;

/**
 *
 * @author Ben
 */
public class EnhancedIO extends DriverStation {
    private static DriverStationEnhancedIO IO;
    /**
     * Constructor. Instantiates IO object.
     */
    public EnhancedIO() {
        IO = Hardware.driverStation.getEnhancedIO();
    }
    /**
     * Gets a digital input.
     * @param channel Which channel to get
     * @return The returned boolean from the DriverStation
     */
    public static boolean getDigital(int channel) {
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
        try {
            IO.setDigitalOutput(channel, value);
        } catch (EnhancedIOException ex) {
            ex.printStackTrace();
        }
    }
}
