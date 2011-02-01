package GLaDOS2011.autono;

import GLaDOS2011.Hardware;
import GLaDOS2011.util.*;

/**
 * Contains code that uses the tape sensors
 * @author Chris Cheng
 * @author Ben
 */
public class AutonoSensors {
    // The last sensor value. Used so that we know what to do when all sensors are false.
    private static int lastCase;
    
    /**
     * For use with Autonomous.  Keeps the robot moving along the line.
     */
    public static void moveOnLine(){
        // Get sensor values and print descriptive message to the driver station
        int sensorValue = getSensors();
        Hardware.txtout.say(3, Utils.toBinary(sensorValue) + ": " + getSensorMessage());
        // Drive based on sensor values
        switch(sensorValue){
            case 1:
                // Go forward
                Hardware.drive.driveAtSpeed(.5, .5);
                break;
            case 7:
                // Stop
                Hardware.drive.driveAtSpeed(0, 0);
                break;
            case 0:
                if(lastCase == 0 || lastCase == 1){
                    // Go right
                    Hardware.drive.driveAtSpeed(.7, .3);
                } else {
                    // Go left
                    Hardware.drive.driveAtSpeed(.3, .7);
                }
                break;
            default:
                // Go left
                Hardware.drive.driveAtSpeed(.3, .7);
                break;
        }
        if(sensorValue != 0 && lastCase != sensorValue){
            // Log sensor data
            FileActions.writeTapeSensors(sensorValue);
            lastCase = sensorValue;
        }
    }

    /**
     * Picks a message that tells the drivers what we want to do, based on the
     * current values of the sensors.
     * @return A descriptive message about what we want to do
     */
    public static String getSensorMessage() {
        switch(getSensors()){
            case 1:
                return "On, go forward        ";
            case 7:
                return "At T, stop            ";
            case 0:
                if(lastCase == 0 || lastCase == 1){
                    return "Off, go right     ";
                } else {
                    return "Off, go left      ";
                }
            default:
                return "Near, go left         ";
        }
    }

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
}
