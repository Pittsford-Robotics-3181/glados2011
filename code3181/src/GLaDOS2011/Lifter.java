package GLaDOS2011;

/**
 * This class contains code for the lifter mechanism.
 * @author Eric Lee
 * A class for the lifter.
 */
public class Lifter {
   // <editor-fold defaultstate="collapsed" desc="Variables">
   public static double heightSensor;
   static double lifterSpeed = 0.3;
   static final double HEIGHT_RANGE = 0.2;

   private static final int MANUAL_MODE = 0;
   private static final int AUTO_FLOOR = 1;
   private static final int AUTO_FIRST_PEG = 2;
   private static final int AUTO_SECOND_PEG = 3;
   private static final int AUTO_THIRD_PEG = 4;
   private static int lifterState = 0;
   // </editor-fold>

   // <editor-fold defaultstate="collapsed" desc="private static void Lifter.goToHeight(double heightUpper)">
    /**
     * Intakes two double values and moves the lifter to a location in between
     * these values.
     * @param heightUpper The target height
     */
    private static void goToHeight(double heightUpper) {
        double heightLower = heightUpper - HEIGHT_RANGE;

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
    // </editor-fold>

   // <editor-fold defaultstate="collapsed" desc="private static void Lifter.abort()">
    /**
     * If button seven is pressed the lift motor is set to stop
     */
    private static void abort() {
        if (Hardware.checkButton(7)) {
            Hardware.lifter.set(0.0);
        }
    }
    // </editor-fold>

   // <editor-fold defaultstate="collapsed" desc="public static void Lifter.controlLifter()">
   /**
    * controlLifter() is a event machine. By default the lifterState is set
    * to MAUNUAL_MODE. Button one and two move the robot up and down. Buttons
    * three through six move the lifter to a designated height.
    */
   public static void controlLifter() {
      switch(lifterState) {
          case MANUAL_MODE:
              if(Hardware.checkButton(7))
                  goToHeight(9.0);
              
              else if(Hardware.checkButton(8))
                  goToHeight(0.2);

              else if(Hardware.checkButton(9))
                  lifterState = AUTO_FLOOR;

              else if(Hardware.checkButton(10))
                  lifterState = AUTO_FIRST_PEG;

              else if(Hardware.checkButton(11))
                  lifterState = AUTO_SECOND_PEG;

              else if(Hardware.checkButton(12))
                  lifterState = AUTO_THIRD_PEG;

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
   // </editor-fold>
}
