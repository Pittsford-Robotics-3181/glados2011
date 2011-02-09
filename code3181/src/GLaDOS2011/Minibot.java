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
            Hardware.minibot.set(true);
        }
    }
}
