package GLaDOS2011;

/**
 * Class to control the claw motors.  This assumes that the motors are connected to the inside.
 * @author Chris Cheng
 */
public class Claw {

    /**
     * Roll the tube in the claw.
     * @param speed The speed at which to roll the tube - a double from -1.0 to 1.0.  Negative rolls downward, Positive rolls upward.
     */
    public static void roll(double speed){
        Hardware.topClaw.set(0.7 * speed);
        Hardware.bottomClaw.set(speed);
    }

    /**
     * Stop the claw from doing something.
     */
    public static void stop(){
        Hardware.topClaw.set(0);
        Hardware.bottomClaw.set(0);
    }

    /**
     * Control the Claw's take in / take out.
     * @param speed The speed at which to suck or spit the tube - a double from -1.0 to 1.0.  Negative spits, Positive sucks.
     */
    public static void tubeIn_Out(double speed){
        Hardware.topClaw.set(speed);
        Hardware.bottomClaw.set(-1.0 * speed);
    }
}