package GLaDOS2011;

import GLaDOS2011.autono.*; // autonomous files
import GLaDOS2011.util.*; // useful utilities
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The main class for our robot
 * @author Chris Cheng
 * @author Ben
 * @author Eric
 */
public class GLaDOS2011 extends IterativeRobot {
    // Autonomous mode
    int autonoMode = 0;
    // Low speed for testing
    final double LOW_SPEED = .15;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Hardware.driverStation = DriverStation.getInstance();
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:    DISABLED");
    }


    //------------$*$*$*$*$*$*$*$*AUTONOMOUS METHODS*$*$*$*$*$*$*$*$------------//

    /**
     * This function is run when autonomous mode begins. It determines which
     * autonomous mode we are using and starts the timer and compressor.
     */
    public void autonomousInit() {
        Hardware.txtout.clearOutput();
        // Determine which autonomous mode we want and tell the drivers
        // The switches are the binary representation of the autonomous mode
        autonoMode = 0;
        // Left worth 4
        autonoMode = autonoMode + 4 * Utils.toInt(Hardware.autonoSwitches[0].get());
        // Middle worth 2
        autonoMode = autonoMode + 2 * Utils.toInt(Hardware.autonoSwitches[1].get());
        // Right worth 1
        autonoMode = autonoMode + 1 * Utils.toInt(Hardware.autonoSwitches[2].get());
        autonoMode = 2;
        Hardware.txtout.say(1, "State:  AUTONOMOUS " + autonoMode);
        
        if(EnhancedIO.getDigital(11)){
            Hardware.txtout.say(2, "MODE: DEAD RECKONING");
        } else {
            Hardware.txtout.say(2, "MODE: SENSORS");
        }
        
        Hardware.gameTimer.reset();
        Hardware.gameTimer.start();

        Hardware.compressor.start();
    }

    /**
     * This function is called periodically during autonomous. It runs the
     * selected autonomous mode.
     */
    public void autonomousPeriodic() {

        updateDashboard();

        // Run the selected autonomous mode
        switch (autonoMode) {
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
            default:
                Autono0.run();
                break;
        }
    }


    //------------$*$*$*$*$*$*$*$*TELEOP METHODS*$*$*$*$*$*$*$*$------------//

    public void teleopInit() {
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:  TELEOPERATED");
        // Test lines
        for(int i=2; i<7; i++)
            Hardware.txtout.say(i, "This is line " + i + ".");
        Hardware.compressor.start();
        Hardware.gameTimer.start();
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {

        updateDashboard();

        double leftSpeed = 0;
        double rightSpeed = 0;
        String message = "";

        // Shift gear based on driver input or current spike (which overrides driver)
        if(Hardware.checkButton(1) && Hardware.gearMode == Hardware.LOW){
            Hardware.drive.shiftGear(Hardware.HIGH);
        } else if(!Hardware.checkButton(1) && Hardware.gearMode == Hardware.HIGH) {
            Hardware.drive.shiftGear(Hardware.LOW);
        }

        // The low speed buttons will probably be removed soon
        if(Hardware.checkButton(2, Hardware.LEFT)){
            // Low speed button for testing
            leftSpeed = LOW_SPEED;
            message = "Low speed  ";
        }
        if(Hardware.checkButton(2, Hardware.RIGHT)){
            // Low speed button for testing
            rightSpeed = LOW_SPEED;
            message = "Low speed  ";
        }
        if(Hardware.checkButton(3)){
            // Stop button
            // This shouldn't be used.
            leftSpeed = 0;
            rightSpeed = 0;
            message = "Stopped    ";
        }

        if(Hardware.checkButton(10, Hardware.LEFT)){
            // Turns off compressor
            Hardware.compressor.stop();
        }
        if(Hardware.checkButton(11, Hardware.LEFT)){
            // Turns on compressor
            Hardware.compressor.start();
        }
        // Actually drive
        Hardware.drive.driveAtSpeed(leftSpeed, rightSpeed);

        // Print out useful messages
        Hardware.txtout.say(4, message + leftSpeed + "," + rightSpeed);
        Hardware.txtout.say(2, "Lifter State: " + Lifter.getState());
        Sensors.printSensorData();

        // Check if the minibot is "unlocked"
        if(Hardware.leftJoystick.getTwist()<-0.75 || Hardware.rightJoystick.getTwist()<-0.75) {
            Minibot.unlocked = true;
        } else {
            Minibot.unlocked = false;
        }

        // Update the alnalog signals from the Cypress Box.
        EnhancedIO.updateAnas();

        // Control the arm, claw, and lifter
        Arm.control();
        Claw.control();
        Lifter.controlLifter();

        // Checks if switch for elbow is on.

        // Check if Minibot is to be deployed.
        if(EnhancedIO.getBoxButton(11)){
            Minibot.deploy();
        }
    }
    

     // <editor-fold defaultstate="collapsed" desc="void GlaDOS2011.updateDashboard()">
    /**
     * Shows data on driver station dashboard. For an explanation, see link:
     * http://www.chiefdelphi.com/forums/showthread.php?t=90167
     */
    void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 4;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 6;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            lowDashData.addByte(Solenoid.getAllFromDefaultModule());

            //What height is the lifter?
            lowDashData.addDouble(Lifter.getHeight());

            //Is the minibot unlocked by the driver?
            lowDashData.addBoolean(Minibot.unlocked);

            //Shape mode
            lowDashData.addByte((byte) Hanging.mode);
        }
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }
    // </editor-fold>
}
