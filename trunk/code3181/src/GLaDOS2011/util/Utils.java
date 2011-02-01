package GLaDOS2011.util;

/**
 * Contains useful functions that don't fit anywhere else.
 * @author Ben
 */
public class Utils {
    /**
     * Converts a boolean to 0 or 1.
     * @param bool The boolean to convert
     * @return 0 if bool=false, 1 if bool=true
     */
    public static int toInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /**
     * Converts value to a 3-digit binary number.
     * @param value The value to convert
     * @return The binary representation of the value (e.g. 011)
     */
    public static String toBinary(int value) {
        String returnString = "";
        returnString += value >> 2;
        returnString += value >> 1 & 1;
        returnString += value & 1;
        return returnString;
    }

    /**
     * Checks if a value is small enough to be considered zero.
     * @param value The value to check
     * @return 0, if value is less than .05; value, otherwise
     */
    public static double checkForSmall(double value) {
        if(value < .05)
            return 0;
        return value;
    }
}
