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
        // Get sensor values and print descriptive message to the driver station
        int sensorValue = getSensors();
        Hardware.txtout.say(3, getSensorMessage());
        // Drive based on sensor values
        switch(sensorValue){
            case 1:
                Hardware.drive.driveAtSpeed(.5, .5);
                break;
            case 7:
                Hardware.drive.driveAtSpeed(0, 0);
                break;
            case 0:
                if(lastCase == 0 || lastCase == 1){
                    Hardware.drive.driveAtSpeed(.7, .3);
                } else {
                    Hardware.drive.driveAtSpeed(.3, .7);
                }
                break;
            default:
                Hardware.drive.driveAtSpeed(.3, .7);
                break;
        }
        if(sensorValue != 0){
            lastCase = getSensors();
        }
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

    public static String getSensorMessage() {
        // Pick descriptive message
        switch(getSensors()){
            case 1:
                return "On tape, go forward   ";
            case 7:
                return "At T, stop            ";
            case 0:
                if(lastCase == 0 || lastCase == 1){
                    return "Off tape, go right    ";
                } else {
                    return "Off tape, go left     ";
                }
            default:
                return "Near tape, go left    ";
        }
    }
}
