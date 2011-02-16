package GLaDOS2011.autono;

import GLaDOS2011.*;
import GLaDOS2011.util.*;

/**
 * Contains code that uses the tape sensors
 * @author Chris Cheng
 * @author Ben
 */
public class Sensors {
    // The last sensor value. Used so that we know what to do when all sensors are false.
    private static int lastCase;
    public static boolean atEnd = false;

    /**
     * Operate the lifter, arm and claw to hang in autonomous on the lower top pegs.
     */
    public static void run() {
        // If at the "T"
        if(Sensors.atEnd){
            // If at the target height
            if(Lifter.closeEnough(Peg.TOP)){
                Lifter.stop();
                Claw.tubeIn_Out(-.5);
            } else {
                Lifter.goToHeight(Peg.TOP);
            }
        } else {
            // Move along the line
            Sensors.moveOnLine();
        }
    }

    /**
     * Operate the lifter, arm and claw to hang in autonomous on the lower top pegs.
     * @param height The target height
     */
    public static void run(double height) {
        // If at the "T"
        if(Sensors.atEnd){
            // If at the target height
            if(Lifter.closeEnough(height)){
                Lifter.stop();
                Claw.tubeIn_Out(-.5);
            } else {
                Lifter.goToHeight(height);
            }
        } else {
            // Move along the line
            Sensors.moveOnLine();
        }
    }

    /**
     * For use with Autonomous.  Keeps the robot moving along the line.
     */
    public static void moveOnLine(){
        // Get sensor values and print descriptive message to the driver station
        int sensorValue = getSensors();
        printSensorData();
        // Drive based on sensor values
        switch(sensorValue){
            case 1:
                // Go forward
                Hardware.drive.driveAtSpeed(.5, .5);
                break;
            case 7:
                // Stop
                atEnd = true;
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
            // Log sensor data (causes a runtime exception)
            //FileActions.writeTapeSensors(sensorValue);
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
                return "On, go forward";
            case 7:
                return "At T, stop";
            case 0:
                if(lastCase == 0 || lastCase == 1){
                    return "Off, go right";
                } else {
                    return "Off, go left";
                }
            default:
                return "Near, go left";
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

    /**
     * Print out a useful message.
     */
    public static void printSensorData() {
        Hardware.txtout.say(3, Utils.toBinary(getSensors()) + ": " + getSensorMessage());
    }
}