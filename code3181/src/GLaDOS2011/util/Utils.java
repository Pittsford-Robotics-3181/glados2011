package GLaDOS2011.util;

/**
 * Contains a useful function that doesn't fit anywhere else.
 * @author Ben
 */
public class Utils {
    // <editor-fold defaultstate="collapsed" desc="public static int Utils.toInt(boolean bool)">
    /**
     * Converts a boolean to 0 or 1.
     * @param bool The boolean to convert
     * @return 0 if bool=false, 1 if bool=true
     */
    public static int toInt(boolean bool) {
        return bool ? 1 : 0;
    }
    // </editor-fold>

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
}
