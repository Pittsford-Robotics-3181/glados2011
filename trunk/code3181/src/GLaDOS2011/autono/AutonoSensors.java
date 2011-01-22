package GLaDOS2011.autono;

import GLaDOS2011.Hardware;
import GLaDOS2011.util.Utils;

/**
 * Contains code that uses the tape sensors
 * @author Chris Cheng
 * @author Ben
 */
public class AutonoSensors {
    private static int lastCase;
    // <editor-fold defaultstate="collapsed" desc="public static void AutonoSensors.moveOnLine()">
    /**
     * For use with Autonomous.  Keeps the robot moving along the line.
     */
    public static void moveOnLine(){
        switch(getSensors()){
            case 1:
                //drive forward
                break;
            case 7:
                //stop
                break;
            case 0:
                if(lastCase == 0 || lastCase == 1){
                    //go right
                } else {
                    //go left
                }
                break;
            default:
                //go left
                break;
        }
        lastCase = getSensors();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="public int AutonoSensors.getSensors()">
    /**
     * Reads the current sensor values and returns the reading as an int.
     * @return The sensor values in binary form
     */
    public static int getSensors() {
        int returnVal = 0;
        // Left worth 4
        returnVal = returnVal + 4 * Utils.toInt(Hardware.leftSensor.get());
        // Middle worth 2
        returnVal = returnVal + 2 * Utils.toInt(Hardware.centerSensor.get());
        // Right worth 1
        returnVal = returnVal + 1 * Utils.toInt(Hardware.rightSensor.get());
        return returnVal;
    }
    // </editor-fold>


}
