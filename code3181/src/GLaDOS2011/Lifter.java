package GLaDOS2011;

/**
 * This class contains code for the lifter mechanism.
 * @author Eric Lee
 * A class for the lifter.
 */
public class Lifter {
   public static double currentHeight; // temporary
   static double lifterSpeed = 0.3;
   static final double HEIGHT_TOLERANCE = 0.2;

   private static final int MANUAL_MODE = 0;
   private static final int AUTO_FLOOR = 1;
   private static final int AUTO_FIRST_PEG = 2;
   private static final int AUTO_SECOND_PEG = 3;
   private static final int AUTO_THIRD_PEG = 4;
   private static int lifterState = 0;


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
     * controlLifter() is a event machine. By default the lifterState is set
     * to MANUAL_MODE. Button one and two move the robot up and down. Buttons
     * three through six move the lifter to a designated height.
     */
    public static void controlLifter() {
      checkState();
      
      switch(lifterState) {
          case MANUAL_MODE:
              if(EnhancedIO.getDigital(1))
                  goToHeight(9.0);

              else if(EnhancedIO.getDigital(2))
                  goToHeight(0.2);

              else
                  stop();
              break;

          case AUTO_FLOOR:
              goToHeight(0.2);
              abort();
              break;

          case AUTO_FIRST_PEG:
              goToHeight(3.0);
              abort();
              break;

          case AUTO_SECOND_PEG:
              goToHeight(6.0);
              abort();
              break;

          case AUTO_THIRD_PEG:
              goToHeight(9.0);
              abort();
              break;

      }
   }

   /**
    * Sets lifterState to the current state, as read from the buttons.
    */
   private static void checkState()
   {
       if(EnhancedIO.getDigital(1))
           lifterState = MANUAL_MODE;

       else if(EnhancedIO.getDigital(2))
           lifterState = MANUAL_MODE;

       else if(EnhancedIO.getDigital(3))
           lifterState = AUTO_FLOOR;

       else if(EnhancedIO.getDigital(4))
           lifterState = AUTO_FIRST_PEG;

       else if(EnhancedIO.getDigital(5))
           lifterState = AUTO_SECOND_PEG;

       else if(EnhancedIO.getDigital(6))
           lifterState = AUTO_THIRD_PEG;
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
