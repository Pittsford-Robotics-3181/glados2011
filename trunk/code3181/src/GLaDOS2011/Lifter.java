package GLaDOS2011;

/**
 * This class contains code for the lifter mechanism.
 * @author Eric Lee
 * A class for the lifter.
 */
public class Lifter {
   public static double heightSensor; // temporary
   static double lifterSpeed = 0.3;
   static final double HEIGHT_TOLERANCE = 0.2;

   private static int lifterZone;
   private static final int ZONE_THREE = 3;
   private static final int ZONE_TWO = 2;
   private static final int ZONE_ONE = 1;

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

        //converts ultrasonic output to feet
        heightTarget = heightTarget * 512.0 / 12;

        double heightUpper = heightTarget + HEIGHT_TOLERANCE;
        double heightLower = heightTarget - HEIGHT_TOLERANCE;

        //Checks if the the height of the lifter is greater than the largest
        //destination value. If so the motor is set to a negative value which
        //should move the lifter down.
        if (heightUpper < heightSensor) {
            Hardware.lifter.set(-lifterSpeed);
        } //Checks if the height of the lifter is less than the smallest
        //destination value.  If so the motor is set to a positive value which
        //should move the lifter up.
        else if (heightLower > heightSensor) {
            Hardware.lifter.set(lifterSpeed);
        } //If all other tests fail the motor is set to 0 which will stop the
        //lifter.
        else {
            Hardware.lifter.set(0.0);
            lifterState = MANUAL_MODE;
        }
    }


    /**
     * If button seven is pressed the lift motor is set to stop
     */
    private static void abort() {
        if (Hardware.checkButton(4)) {
            Hardware.lifter.set(0.0);
        }
    }


  
    /* SAVE FOR LATER... change name of method

     * public static void Lifter()
    {
        if(lifterState != MANUAL_MODE)
        {
            if(Hardware.checkButton(6))
                lifterState = lifterState + 1;
            else if(Hardware.checkButton(7))
                lifterState = lifterState - 1;
            else if(Hardware.checkButton(11) || Hardware.checkButton(10))
                lifterState = MANUAL_MODE;
            controlLifter();
        }
        else
        {
            checkZone();
            lifterAuto();
            controlLifter();
        }
    }*/

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
                  Hardware.lifter.set(0.0);
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
    * Checks to see which zone the lifter is in.
    */
   private static void checkZone() {
       if(heightSensor > 6.0 && heightSensor < 9.0 )
           lifterZone = ZONE_THREE;
       if(heightSensor > 3.0 && heightSensor < 6.0)
           lifterZone = ZONE_TWO;
       if (heightSensor > 0.0 && heightSensor < 3.0)
           lifterZone = ZONE_ONE;
   }
   
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


   /* SAVE FOR LATER
    private static void lifterAuto()
   {
       switch(lifterZone)
       {
           case ZONE_THREE:
               if(Hardware.checkButton(6))
                   lifterState = AUTO_THIRD_PEG;
               else if(Hardware.checkButton(7))
                   lifterState = AUTO_SECOND_PEG;
               break;

           case ZONE_TWO:
               if(Hardware.checkButton(6))
                   lifterState = AUTO_SECOND_PEG;
               else if(Hardware.checkButton(7))
                   lifterState = AUTO_FIRST_PEG;
               break;

           case ZONE_ONE:
               if(Hardware.checkButton(6))
                   lifterState = AUTO_FIRST_PEG;
               else if(Hardware.checkButton(7))
                   lifterState = AUTO_FLOOR;
               break;


       }
   }*/

   /**
    * Returns the state in a string.
    * @return A 4-letter String that signifies the lifterState.
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
       return Math.sin(Hardware.gameTimer.get()/2)*9;
   }
}
