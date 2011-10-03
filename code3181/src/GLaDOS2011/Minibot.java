package GLaDOS2011;

/**
 * Class containing the method to deploy the minibot.
 * @author Chris Cheng
 */
public class Minibot {
    //Is the driver OK with deploying the minibot?
    public static boolean unlocked;
    /**
     * Deploy the minibot.  Herp.
     */
    public static void deploy(){
        if(unlocked) {
            Hardware.minibotIn.set(false);
            Hardware.minibotOut.set(true);
        }
    }

    /**
     * Retracts the arm.
     */
    public static void retract(){
        Hardware.minibotIn.set(true);
        Hardware.minibotOut.set(false);
    }
}
