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
    public static int lifterState = 0;
    public static double destination = 0.0; //Diagnostics variable

    // THIS SPEED PROBABLY NEEDS TO BE CHANGED.                                                                                         *
    public static final double LIFTER_SPEED = 0.5;
    // HEIGHT_TOLERANCE PROBABLY NEEDS TO BE CHANGED.                                                                                   *
    private static final double HEIGHT_TOLERANCE = 0.15;
    private static final int MANUAL_MODE = 0;
    private static final int AUTO_FLOOR = 1;
    private static final int AUTO_FIRST_PEG = 2;
    private static final int AUTO_SECOND_PEG = 3;
    private static final int AUTO_THIRD_PEG = 4;
    private static final int FEEDER_SLOT = 5;
    private static final int HOME = 6;

    //public static boolean liftBreakOn = true;
    //public static double endTime;


    /**
     * Intakes two double values and moves the lifter to a location in between
     * these values.
     * @param heightUpper The target height
     */
    public static void goToHeight(double heightTarget) {
        currentHeight = getHeight();
        destination = heightTarget;

        //converts ultrasonic output to feet


        double heightUpper = heightTarget + HEIGHT_TOLERANCE;
        double heightLower = heightTarget - HEIGHT_TOLERANCE;

        //Checks if the the height of the lifter is greater than the largest
        //destination value. If so, move the lifter down.
        if (heightUpper < currentHeight) {
            goDown();
        } //Checks if the height of the lifter is less than the smallest
        //destination value.  If so, move the lifter up.
        else if (heightLower > currentHeight) {
            goUp();
        } //If all other tests fail, stop the lifter.
        else {
            stop();
        }
    }

    /*
     * THIS NEEDS TO BE CHECKED.                                                                                                        *
     * This is probably the going down method
     */
    private static void goUp(){
/*           if(liftBreakOn){
                //If this is the first call of this method, set an end time
                endTime = Hardware.gameTimer.get()+100*1000; // get() returns microseconds, apparently
            }

            liftBreakOn = false;*/
            Hardware.liftBreak.set(Relay.Value.kForward);

            //if(Hardware.gameTimer.get() > endTime){
                Hardware.lifter.set(-LIFTER_SPEED);
            //}
    }

    /*
     * This is probably the going up method
     */
    private static void goDown(){
/*            if(liftBreakOn){
                //If this is the first call of this method, set an end time
                endTime = Hardware.gameTimer.get()+100*1000; // get() returns microseconds, apparently
            }

            liftBreakOn = false;*/
            Hardware.liftBreak.set(Relay.Value.kForward);

            //if(Hardware.gameTimer.get() > endTime){
                Hardware.lifter.set(LIFTER_SPEED*2);
            //}

    }    private static void goUpSlowly(){
/*           if(liftBreakOn){
                //If this is the first call of this method, set an end time
                endTime = Hardware.gameTimer.get()+100*1000; // get() returns microseconds, apparently
            }

            liftBreakOn = false;*/
            Hardware.liftBreak.set(Relay.Value.kForward);

            //if(Hardware.gameTimer.get() > endTime){
                Hardware.lifter.set(-LIFTER_SPEED/2);
            //}
    }

    /*
     * This is probably the going up method
     */
    private static void goDownSlowly(){
/*            if(liftBreakOn){
                //If this is the first call of this method, set an end time
                endTime = Hardware.gameTimer.get()+100*1000; // get() returns microseconds, apparently
            }

            liftBreakOn = false;*/
            Hardware.liftBreak.set(Relay.Value.kForward);

            //if(Hardware.gameTimer.get() > endTime){
                Hardware.lifter.set(LIFTER_SPEED);
            //}

    }

    /**
     * Stop the lifter.
     */
    public static void stop() {
/*        if(!liftBreakOn){
                //If this is the first call of this method, set an end time
            endTime = Hardware.gameTimer.get()+50*1000; // get() returns microseconds, apparently
        }
*/
        Hardware.lifter.set(0.0);
        //liftBreakOn = true;
        
        //if(Hardware.gameTimer.get() > endTime){
            Hardware.liftBreak.set(Relay.Value.kOff);
            lifterState = MANUAL_MODE;
        //}
    }

    /**
     * control() is a state machine. By default, lifterState is set to
     * MANUAL_MODE. Buttons one and two move the robot up and down. Buttons
     * three through six move the lifter to a designated height.
     */
    public static void control() {
      checkState();

      //If a claw button is being pressed, do NOT move the lifter.
      if(GLaDOS2011.getBoxButton(7) || GLaDOS2011.getBoxButton(8) || GLaDOS2011.getBoxButton(9) || GLaDOS2011.getBoxButton(10)) {
          stop();
          return;
      }

      switch(lifterState) {
          case MANUAL_MODE:
              if(GLaDOS2011.getBoxButton(16)){ // lifter up
                  goUp();
                  Hardware.txtout.say(3,"Going up");
              }
              else if(GLaDOS2011.getBoxButton(17)){ // lifter down
                  goDown();
                  Hardware.txtout.say(3,"Going down");
              }
              else if(GLaDOS2011.getBoxButton(14)){ // lifter down
                  goUpSlowly();
                  Hardware.txtout.say(3,"Going up slowly");
              }
              else if(GLaDOS2011.getBoxButton(12)){ // lifter down
                  goDownSlowly();
                  Hardware.txtout.say(3,"Going down slowly");
              }
              else {
                  stop();
                  Hardware.txtout.say(3,"Stopping");
              }
              break;

          case AUTO_FLOOR:
              goToHeight(Hanging.FLOOR);
              Hardware.txtout.say(3,"Floor");
              break;

          case AUTO_FIRST_PEG:
              if(Hanging.mode == Hanging.CIRCLE){
                  goToHeight(Hanging.BOTTOM + Hanging.CENTER_OFFSET);
                  Hardware.txtout.say(3,"1st peg: circle");
              }
              else{
                goToHeight(Hanging.BOTTOM);
                Hardware.txtout.say(3,"1st peg: other");
              }
              break;

          case AUTO_SECOND_PEG:
              if(Hanging.mode == Hanging.CIRCLE){
                  goToHeight(Hanging.MIDDLE + Hanging.CENTER_OFFSET);
                  Hardware.txtout.say(3,"2nd peg: circle");
              }
              else{
                  goToHeight(Hanging.MIDDLE);
                  Hardware.txtout.say(3,"2nd peg: other");
              }
              break;

          case AUTO_THIRD_PEG:
              if(Hanging.mode == Hanging.CIRCLE){
                  goToHeight(Hanging.TOP + Hanging.CENTER_OFFSET);
                  Hardware.txtout.say(3,"3rd peg: circle");
              }
              else{
                  goToHeight(Hanging.TOP);
                  Hardware.txtout.say(3,"3rd peg: other");
              }
              break;

          case FEEDER_SLOT:
              goToHeight(Hanging.SLOT);
              Hardware.txtout.say(3,"Feeder");
              break;

          case HOME:
              goToHeight(Hanging.FLOOR);
              Hardware.txtout.say(3,"Home=floor");
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
       else if(GLaDOS2011.getBoxButton(14)){
           lifterState = MANUAL_MODE;
           System.out.print("Going down!");
       }
       else if(GLaDOS2011.getBoxButton(12)){
           lifterState = MANUAL_MODE;
           System.out.print("Going up!");
       }/*
       else if(GLaDOS2011.getBoxButton(13))
           lifterState = AUTO_SECOND_PEG;

       else if(GLaDOS2011.getBoxButton(3))
           lifterState = AUTO_FLOOR;

       else if(GLaDOS2011.getBoxButton(1))
           lifterState = FEEDER_SLOT;
       else if(GLaDOS2011.getBoxButton(2))
           lifterState = HOME;*/

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
