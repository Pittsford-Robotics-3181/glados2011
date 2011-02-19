package GLaDOS2011;

/**
 * This class contains code for the lifter mechanism.
 * @author Eric Lee
 * A class for the lifter.
 */
public class Lifter {
    public static double currentHeight;
    public static double lifterSpeed = 0.3;
    public static int lifterState = 0;

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

        //converts ultrasonic output to feet
        currentHeight = currentHeight * 102.4 / 12.0;

        double heightUpper = heightTarget + HEIGHT_TOLERANCE;
        double heightLower = heightTarget - HEIGHT_TOLERANCE;

        //Checks if the the height of the lifter is greater than the largest
        //destination value. If so the motor is set to a negative value which
        //should move the lifter down.
        if (heightUpper < currentHeight) {
            Hardware.lifter.set(-lifterSpeed);
        } //Checks if the height of the lifter is less than the smallest
        //destination value.  If so the motor is set to a positive value which
        //should move the lifter up.
        else if (heightLower > currentHeight) {
            Hardware.lifter.set(lifterSpeed);
        } //If all other tests fail, stop the lifter.
        else {
            stop();
        }
    }

    /**
     * Stop the lifter.
     */
    public static void stop() {
        Hardware.lifter.set(0.0);
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
              if(EnhancedIO.getBoxButton(16))
                  goToHeight(Hanging.TOP);

              else if(EnhancedIO.getBoxButton(17))
                  goToHeight(Hanging.FLOOR);

              else
                  stop();
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
   private static void checkState()
   {
       if(EnhancedIO.getBoxButton(16))
           lifterState = MANUAL_MODE;

       else if(EnhancedIO.getBoxButton(17))
           lifterState = MANUAL_MODE;

       else if(EnhancedIO.getBoxButton(3))
           lifterState = AUTO_FLOOR;

       else if(EnhancedIO.getBoxButton(14))
           lifterState = AUTO_FIRST_PEG;

       else if(EnhancedIO.getBoxButton(13))
           lifterState = AUTO_SECOND_PEG;

       else if(EnhancedIO.getBoxButton(12))
           lifterState = AUTO_THIRD_PEG;
       else if(EnhancedIO.getBoxButton(1))
           lifterState = FEEDER_SLOT;
       else if(EnhancedIO.getBoxButton(2))
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
       return Math.sin(Hardware.gameTimer.get()/1.5)*4+4;
       // This next line will likely replace the above line
       //return Hardware.heightSensor.getVoltage();
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
