package GLaDOS2011;

import GLaDOS2011.autono.*; // autonomous files
import GLaDOS2011.util.*; // useful utilities
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.tAccelChannel;
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

    public static double ana2; // Buttons 1-6
    public static double ana3; // Buttons 7-10
    public static double ana1; // Buttons 12-14
    public static boolean digital2; // Arm control
    public static boolean digital4; // Three-way toggle for lifter
    public static boolean digital6; // Three-way toggle for lifter
    public static boolean digital8; // Minibot deployment
    // Accelerometer values
    public static double AccelX;
    public static double AccelY;
    public static double AccelZ;


    /**
     * This function is run when the robot starts up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:    DISABLED");
        Hardware.driverStation = DriverStation.getInstance();
        Hardware.dseio = Hardware.driverStation.getEnhancedIO();
        System.out.println("Init......");
    }

    /**
     * This function is called periodically during disabled. It determines the
     * autonomous mode that we want, based on the Cypress accelerometer.
     */
    public void disabledPeriodic() {
        updateEnhancedIO();

        // Determine which autonomous mode we want and tell the drivers
        autonoMode = 0;
        if(Utils.checkForSmall(AccelX) == 0 && Utils.checkForSmall(AccelY) > 0 && Utils.checkForSmall(AccelZ) == 0)
            autonoMode = 1;
        else if(Utils.checkForSmall(AccelY) == 0 && Utils.checkForSmall(AccelZ) == 0){
            if(Utils.checkForSmall(AccelX) < 0)
                autonoMode = 2;
            else if(Utils.checkForSmall(AccelX) > 0)
                autonoMode = 3;
        }
        Hardware.txtout.say(2, "Autonomous mode " + autonoMode);
    }


    //------------$*$*$*$*$*$*$*$*AUTONOMOUS METHODS*$*$*$*$*$*$*$*$------------//

    /**
     * This function is run when autonomous mode begins. It determines which
     * autonomous mode we are using and starts the timer and compressor. It also
     * updating the EnhancedIO inputs.
     */
    public void autonomousInit() {
        Hardware.txtout.clearOutput();

        updateEnhancedIO();

        // Determine which autonomous mode we want and tell the drivers
        // The switches are the binary representation of the autonomous mode
        autonoMode = 0;
        if(Utils.checkForSmall(AccelX) == 0 && Utils.checkForSmall(AccelY) > 0 && Utils.checkForSmall(AccelZ) == 0)
            autonoMode = 1;
        else if(Utils.checkForSmall(AccelY) == 0 && Utils.checkForSmall(AccelZ) == 0){
            if(Utils.checkForSmall(AccelX) < 0)
                autonoMode = 2;
            else if(Utils.checkForSmall(AccelX) > 0)
                autonoMode = 3;
        }
        Hardware.txtout.say(1, "State:  AUTONOMOUS " + autonoMode);
        
        if(true){
            Hardware.txtout.say(2, "MODE: DEAD RECKONING");
        } else {
            Hardware.txtout.say(2, "MODE: SENSORS");
        }

        Hardware.compressor.start();
        Hardware.gameTimer.start();
        Hardware.gameTimer.reset();
    }

    /**
     * This function is called periodically during autonomous. It runs the
     * selected autonomous mode.
     */
    public void autonomousPeriodic() {

        updateDashboard();
        Autonomous.printSensorData();

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

    /**
     * This function is run when teleop mode begins. It starts the timer and
     * compressor and updates the EnhancedIO inputs.
     */
    public void teleopInit() {
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:  TELEOPERATED");

        Hardware.compressor.start();
        Hardware.gameTimer.start();

        updateEnhancedIO();
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
        
        updateDashboard();
        updateEnhancedIO();

        //**************** BEGIN DRIVE CODE ****************//
        double leftSpeed = Hardware.leftJoystick.getY();
        double rightSpeed = Hardware.rightJoystick.getY();
        String message = "";

        // Shift gear based on driver input
        if(Hardware.checkButton(1) && Hardware.gearMode == Hardware.LOW){
            Hardware.drive.shiftGear(Hardware.HIGH);
        } else if(!Hardware.checkButton(1) && Hardware.gearMode == Hardware.HIGH) {
            Hardware.drive.shiftGear(Hardware.LOW);
        }

        // Pick how to interpret (or not interpret) the joystick input.
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
        Hardware.txtout.say(5, "Lifter Speed: " + Hardware.lifter.get());
        Hardware.txtout.say(6, "Lifter Mode:  " + Hanging.mode );
        Autonomous.printSensorData();

        //**************** END DRIVE CODE ****************//



        //System.out.print("Button Up:" + getBoxButton(17) + " Button Down:" + getBoxButton(16) + "\n");

        // Check if the minibot is "unlocked"
        if(Hardware.leftJoystick.getTwist() < -0.75 || Hardware.rightJoystick.getTwist() < -0.75) {
            Minibot.unlocked = true;
        } else {
            Minibot.unlocked = false;
        }

        // Control the arm, claw, and lifter
        Arm.control();
        Claw.control();
        Lifter.control();
        Hanging.updateMode();

        // Check if Minibot is to be deployed.
        if(getBoxButton(11)){
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


    /**
     * Update the analog and digital inputs from the EnhancedIO module.
     */
    public void updateEnhancedIO() {
        try{
            ana1 = Hardware.dseio.getAnalogIn(1);
            ana3 = Hardware.dseio.getAnalogIn(3);
            ana2 = Hardware.dseio.getAnalogIn(2);
            digital2 = Hardware.dseio.getDigital(2);
            digital4 = Hardware.dseio.getDigital(4);
            digital6 = Hardware.dseio.getDigital(6);
            digital8 = Hardware.dseio.getDigital(8);
            AccelX = Hardware.dseio.getAcceleration(tAccelChannel.kAccelX);
            AccelY = Hardware.dseio.getAcceleration(tAccelChannel.kAccelY);
            AccelZ = Hardware.dseio.getAcceleration(tAccelChannel.kAccelZ);
        } catch (EnhancedIOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * This checks whether a specific button on the button box is being pressed
     * (this can also check the toggle switches). It is unclear why the bounds
     * for ana2 overlap.
     * @param button Which button (or toggle) to check
     * @return Whether the button is being pressed, or the toggle is true.
     */
    public static boolean getBoxButton(int button) {

        switch(button){
            case(1):
                if(ana2 > 0.20 && ana2 < 0.86) return true;
                else return false;
            case(2):
                if(ana2 > 0.74 && ana2 < 1.40) return true;
                else return false;
            case(3):
                if(ana2 > 1.27 && ana2 < 1.93) return true;
                else return false;
            case(4):
                if(ana2 > 1.81 && ana2 < 2.47) return true;
                else return false;
            case(5):
                if(ana2 > 2.34 && ana2 < 3.00) return true;
                else return false;
            case(6):
                if(ana2 > 2.89 && ana2 < 3.55) return true;
                else return false;
            case(7):
                if(ana3 > 0.47 && ana3 < 1.13) return true;
                else return false;
            case(8):
                if(ana3 > 1.27 && ana3 < 1.93) return true;
                else return false;
            case(9):
                if(ana3 > 2.08 && ana3 < 2.74) return true;
                else return false;
            case(10):
                if(ana3 > 2.88 && ana3 < 3.54) return true;
                else return false;
            case(11):
                return !digital8;
            case(12):
                if(ana1 > 0.72 && ana1 < 1.38) return true;
                else return false;
            case(13):
                if(ana1 > 1.77 && ana1 < 2.43) return true;
                else return false;
            case(14):
                if(ana1 > 2.83 && ana1 < 3.49) return true;
                else return false;
            case(15):
                return !digital2;
            case(16):
                return !digital4;
            case(17):
                return !digital6;
            default:
                return false;
        }
    }
}
