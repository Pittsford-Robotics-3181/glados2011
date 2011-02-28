package GLaDOS2011;

import edu.wpi.first.wpilibj.Relay;

/**
 * This class contains code for the lifter mechanism.
 * @author Eric Lee
 * @author Chris Cheng
 * A class for the lifter.
 */
public class Lifter {
    public static double currentHeight;
    public static double lifterSpeed = 0.3;
    public static int lifterState = 0;
    public static double destination = 0.0; //Diagnostics variable

    private static final double HEIGHT_TOLERANCE = 0.15;
    private static final int MANUAL_MODE = 0;
    private static final int AUTO_FLOOR = 1;
    private static final int AUTO_FIRST_PEG = 2;
    private static final int AUTO_SECOND_PEG = 3;
    private static final int AUTO_THIRD_PEG = 4;
    private static final int FEEDER_SLOT = 5;
    private static final int HOME = 6;


    /**
     * Intakes two double values and moves the lifter to a location in between
     * these values.
     * @param heightUpper The target height
     */
    public static void goToHeight(double heightTarget) {
        currentHeight = Hardware.heightSensor.getVoltage();
        destination = heightTarget;

        //converts ultrasonic output to feet


        double heightUpper = heightTarget + HEIGHT_TOLERANCE;
        double heightLower = heightTarget - HEIGHT_TOLERANCE;

        //If a claw button is being pressed, do NOT move the elevator.
        if(GLaDOS2011.getBoxButton(7) || GLaDOS2011.getBoxButton(8) || GLaDOS2011.getBoxButton(9) || GLaDOS2011.getBoxButton(10)) {
            stop();
        }
        //Checks if the the height of the lifter is greater than the largest
        //destination value. If so the motor is set to a negative value which
        //should move the lifter down.
        if (heightUpper < currentHeight) {
            goUp();
        } //Checks if the height of the lifter is less than the smallest
        //destination value.  If so the motor is set to a positive value which
        //should move the lifter up.
        else if (heightLower > currentHeight) {
            goDown();
        } //If all other tests fail, stop the lifter.
        else {
            stop();
        }
    }

    private static void goUp(){
            Hardware.liftBreak.set(Relay.Value.kForward);
            Hardware.lifter.set(-lifterSpeed);
    }
    private static void goDown(){
            Hardware.liftBreak.set(Relay.Value.kForward);
            Hardware.lifter.set(lifterSpeed*2);
    }

    /**
     * Stop the lifter.
     */
    public static void stop() {
        Hardware.lifter.set(0.0);
        Hardware.liftBreak.set(Relay.Value.kOff);
        lifterState = MANUAL_MODE;
    }

    /**
     * If button seven is pressed the lift motor is set to stop
     */
    private static void abort() {
        if (Hardware.checkButton(4)) {
            stop();
        }
    }

    /**
     * control() is a state machine. By default, lifterState is set to
     * MANUAL_MODE. Buttons one and two move the robot up and down. Buttons
     * three through six move the lifter to a designated height.
     */
    public static void controlLifter() {
      checkState();
      
      switch(lifterState) {
          case MANUAL_MODE:
              if(GLaDOS2011.getBoxButton(16)){ //Elevator Up
                  goUp();
              }
              else if(GLaDOS2011.getBoxButton(17)){ //Elevator Down
                  goDown();
              }
              else {
                  stop();
              }
              break;

          case AUTO_FLOOR:
              goToHeight(Hanging.FLOOR);
              break;

          case AUTO_FIRST_PEG:
              if(Hanging.mode == Hanging.CIRCLE)
                  goToHeight(Hanging.BOTTOM + Hanging.CENTER_OFFSET);
              else
              goToHeight(Hanging.BOTTOM);
              break;

          case AUTO_SECOND_PEG:
              if(Hanging.mode == Hanging.CIRCLE)
                  goToHeight(Hanging.MIDDLE + Hanging.CENTER_OFFSET);
              goToHeight(Hanging.MIDDLE);
              break;

          case AUTO_THIRD_PEG:
              if(Hanging.mode == Hanging.CIRCLE)
                  goToHeight(Hanging.TOP + Hanging.CENTER_OFFSET);
              else
              goToHeight(Hanging.TOP);
              break;

          case FEEDER_SLOT:
              goToHeight(Hanging.SLOT);
              break;

          case HOME:
              goToHeight(Hanging.FLOOR);
              break;

      }
   }

   /**
    * Sets lifterState to the current state, as read from the buttons.
    */
   private static void checkState(){
       if(GLaDOS2011.getBoxButton(16)){
           lifterState = MANUAL_MODE;
           System.out.print("Going up!");
       }

       else if(GLaDOS2011.getBoxButton(17)){
           lifterState = MANUAL_MODE;
           System.out.print("Going down!");
       }
       else if(GLaDOS2011.getBoxButton(3))
           lifterState = AUTO_FLOOR;

       else if(GLaDOS2011.getBoxButton(14))
           lifterState = AUTO_FIRST_PEG;

       else if(GLaDOS2011.getBoxButton(13))
           lifterState = AUTO_SECOND_PEG;

       else if(GLaDOS2011.getBoxButton(12))
           lifterState = AUTO_THIRD_PEG;
       else if(GLaDOS2011.getBoxButton(1))
           lifterState = FEEDER_SLOT;
       else if(GLaDOS2011.getBoxButton(2))
           lifterState = HOME;

   }

   /**
    * Returns the state in a string.
    * @return A 4-letter string that signifies the lifter state.
    * @author Chris Cheng
    */
   public static String getState(){
       switch(lifterState){
           case AUTO_FLOOR:
               return "FLR ";
           case AUTO_FIRST_PEG:
               return "Peg1";
           case AUTO_SECOND_PEG:
               return "Peg2";
           case AUTO_THIRD_PEG:
               return "Peg3";
           default:
               return "MAN ";
       }
   }

   /**
    * Gets the current height of the lifter. Doesn't work right now.
    * @return The height in feet
    */
   public static double getHeight() {
    //   return Math.sin(Hardware.gameTimer.get()/1.5)*4+4;
       // This next line will likely replace the above line
       return Hardware.heightSensor.getVoltage() * 102.4 / 12.0;
   }

   /**
    * Check if the lifter is at a height close enough to be considered the same
    * as the target height.
    * @param height The target height
    * @return Whether or not the lifter is close enough
    */
   public static boolean closeEnough(double height){
       return Math.abs(height - currentHeight) < HEIGHT_TOLERANCE;
   }
}
