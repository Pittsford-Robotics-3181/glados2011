package GLaDOS2011;

import GLaDOS2011.autono.*; // autonomous files
import GLaDOS2011.util.*; // useful utilities
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
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

    public static double Ana2;
    public static double Ana3;
    public static double Ana1;
    public static boolean Digital2;
    public static boolean Digital4;
    public static boolean Digital6;
    public static boolean Digital8;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        
        Hardware.txtout.clearOutput();
        Hardware.txtout.say(1, "State:    DISABLED");
        Hardware.driverStation = DriverStation.getInstance();
        Hardware.dseio = Hardware.driverStation.getEnhancedIO();
        System.out.println("Init......");
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
        
        if(true){
            Hardware.txtout.say(2, "MODE: DEAD RECKONING");
        } else {
            Hardware.txtout.say(2, "MODE: SENSORS");
        }
        
        Hardware.gameTimer.reset();
        Hardware.gameTimer.start();

        Hardware.compressor.start();

        try{
            Ana1 = Hardware.dseio.getAnalogIn(1);
            Ana3 = Hardware.dseio.getAnalogIn(3);
            Ana2 = Hardware.dseio.getAnalogIn(2);
            Digital2 = Hardware.dseio.getDigital(2);
            Digital4 = Hardware.dseio.getDigital(4);
            Digital6 = Hardware.dseio.getDigital(6);
            Digital8 = Hardware.dseio.getDigital(8);
        } catch (EnhancedIOException ex) {
            System.out.println(ex);
        }
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
        try{
            Ana1 = Hardware.dseio.getAnalogIn(1);
            Ana3 = Hardware.dseio.getAnalogIn(3);
            Ana2 = Hardware.dseio.getAnalogIn(2);
            Digital2 = Hardware.dseio.getDigital(2);
            Digital4 = Hardware.dseio.getDigital(4);
            Digital6 = Hardware.dseio.getDigital(6);
            Digital8 = Hardware.dseio.getDigital(8);
        } catch (EnhancedIOException ex) {
           System.out.println(ex);
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {

        updateDashboard();
        try{
            Ana1 = Hardware.dseio.getAnalogIn(1);
            Ana3 = Hardware.dseio.getAnalogIn(3);
            Ana2 = Hardware.dseio.getAnalogIn(2);
            Digital2 = Hardware.dseio.getDigital(2);
            Digital4 = Hardware.dseio.getDigital(4);
            Digital6 = Hardware.dseio.getDigital(6);
            Digital8 = Hardware.dseio.getDigital(8);
        } catch (EnhancedIOException ex) {
           System.out.println(ex);
        }

/****************************** BEGIN DRIVE CODE ******************************************/
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
        Hardware.drive.driveAtSpeed(leftSpeed, rightSpeed/2);

        // Print out useful messages
        Hardware.txtout.say(4, message + leftSpeed + "," + rightSpeed);
        Hardware.txtout.say(2, "Lifter State: " + Lifter.getState());
        Hardware.txtout.say(5, "Lifter Speed: " + Hardware.lifter.get());
        Hardware.txtout.say(6, "Lifter Mode:  " + Hanging.mode );
        Autonomous.printSensorData();

/****************************** END DRIVE CODE ******************************************/



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
        // Checks if switch for elbow is on.

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


    public static boolean getBoxButton(int button) {

        switch(button){
            case(1):
                if(Ana2 > 0.20 && Ana2 < 0.86) return true;
                else return false;
            case(2):
                if(Ana2 > 0.74 && Ana2 < 1.40) return true;
                else return false;
            case(3):
                if(Ana2 > 1.27 && Ana2 < 1.93) return true;
                else return false;
            case(4):
                if(Ana2 > 1.81 && Ana2 < 2.47) return true;
                else return false;
            case(5):
                if(Ana2 > 2.34 && Ana2 < 3.00) return true;
                else return false;
            case(6):
                if(Ana2 > 2.89 && Ana2 < 3.55) return true;
                else return false;
            case(7):
                if(Ana3 > 0.47 && Ana3 < 1.13) return true;
                else return false;
            case(8):
                if(Ana3 > 1.27 && Ana3 < 1.93) return true;
                else return false;
            case(9):
                if(Ana3 > 2.08 && Ana3 < 2.74) return true;
                else return false;
            case(10):
                if(Ana3 > 2.88 && Ana3 < 3.54) return true;
                else return false;
            case(11):
                return !Digital8;
            case(12):
                if(Ana1 > 0.72 && Ana1 < 1.38) return true;
                else return false;
            case(13):
                if(Ana1 > 1.77 && Ana1 < 2.43) return true;
                else return false;
            case(14):
                if(Ana1 > 2.83 && Ana1 < 3.49) return true;
                else return false;
            case(15):
                return !Digital2;
            case(16):
                return !Digital4;
            case(17):
                return !Digital6;
            default:
                return false;
        }
    }
}
