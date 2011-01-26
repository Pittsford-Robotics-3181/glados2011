package GLaDOS2011;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation;
import GLaDOS2011.autono.*; // autonomous files
import GLaDOS2011.util.*; // useful utilities

/**
 * The main class for our robot
 * @author Chris Cheng
 * @author Ben
 * @author Eric
 */
public class GLaDOS2011 extends IterativeRobot {
    // <editor-fold defaultstate="collapsed" desc="Variables">
    // Autonomous mode
    int autonoMode = 0;
    // Driver station
    DriverStation driverStation;
    // Low speed for testing
    final double LOW_SPEED = .05;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void robotInit()">
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        driverStation = DriverStation.getInstance();
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:    DISABLED    ");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Autonomous Methods">
    // <editor-fold defaultstate="collapsed" desc="public void autonomousInit()">
    /**
     * This function is run when autonomous mode begins. It determines which
     * autonomous mode we are using.
     */
    public void autonomousInit() {
        Hardware.txtout.clearOutput();
        // Determine which autonomous mode we want and tell the drivers
        // The switches are the binary representation of the autonomous mode
        // Left worth 4
        autonoMode = autonoMode + 4 * Utils.toInt(Hardware.autonoSwitches[0].get());
        // Middle worth 2
        autonoMode = autonoMode + 2 * Utils.toInt(Hardware.autonoSwitches[1].get());
        // Right worth 1
        autonoMode = autonoMode + 1 * Utils.toInt(Hardware.autonoSwitches[2].get());

        Hardware.txtout.say(1, "State:  AUTONOMOUS " + autonoMode + "  ");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void autonomousPeriodic()">
    /**
     * This function is called periodically during autonomous. It runs the
     * selected autonomous mode.
     */
    public void autonomousPeriodic() {
        // Run the selected autonomous mode
        switch (autonoMode) {
            case 0:
                Autono0.run();
                break;
            case 1:
                Autono1.run();
                break;
            case 2:
                Autono2.run();
                break;
            case 3:
                Autono3.run();
                break;
            case 4:
                Autono4.run();
                break;
        }
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Teleoperated Methods">

    // <editor-fold defaultstate="collapsed" desc="public void teleopInit()">
    public void teleopInit() {
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:  TELEOPERATED  ");
        // Test lines
        for(int i=2; i<7; i++)
            Hardware.txtout.say(i, "This is line "+i+".");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void teleopPeriodic()">
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Drive
        double leftSpeed = 0;
        double rightSpeed = 0;
        String message = "";

        if(Hardware.checkButton(1)){
            // 70% speed button
            leftSpeed = Hardware.leftJoystick.getY() * .7;
            rightSpeed = Hardware.rightJoystick.getY() * .7;
            message = "70% speed             ";
        } else {
            // Regular speed
            leftSpeed = Hardware.leftJoystick.getY();
            rightSpeed = Hardware.rightJoystick.getY();
            message = "Regular drive         ";
        }
        // The low speed buttons will probably be removed soon
        if(Hardware.checkButton(2, Hardware.LEFT)){
            // Low speed button for testing
            leftSpeed = LOW_SPEED;
            message = "Low speed             ";
        }
        if(Hardware.checkButton(2, Hardware.RIGHT)){
            // Low speed button for testing
            rightSpeed = LOW_SPEED;
            message = "Low speed             ";
        }
        if(Hardware.checkButton(3)){
            // Stop button
            // This shouldn't be used.
            leftSpeed = 0;
            rightSpeed = 0;
            message = "Stopped               ";
        }
        Hardware.drive.driveAtSpeed(leftSpeed, rightSpeed);
        Hardware.txtout.say(4, message);

        // Calls method from lifter that controls the the forklift
        Lifter.controlLifter();
    }
    // </editor-fold>
    // </editor-fold>

}
