package GLaDOS2011;

/**
 * Class to control the claw motors.  This assumes that the motors are connected to the inside.
 * @author Chris Cheng
 */
public class Claw {

    /**
     * Roll the tube in the claw.
     * @param up TRUE to roll the other end of the tube up, FALSE to bring it down.
     */
    public void roll(boolean up, double speed){
        if(up){
            Hardware.topClaw.set(0.7 * speed);
            Hardware.bottomClaw.set(speed);
        } else {
            Hardware.topClaw.set(-0.7 * speed);
            Hardware.bottomClaw.set(-1.0 * speed);
        }
    }

    /**
     * Stop the claw from doing something.
     */
    public void stop(){
        Hardware.topClaw.set(0);
        Hardware.bottomClaw.set(0);
    }

    /**
     * Control the Claw's take in / take out.
     * @param suck TRUE to suck it in.  FALSE to suck it out.
     */
    public void claw(boolean suck, double speed){
        if(suck){
            Hardware.topClaw.set(speed);
            Hardware.bottomClaw.set(-1.0 * speed);
        } else {
            Hardware.topClaw.set(-1.0 * speed);
            Hardware.bottomClaw.set(speed);
        }
    }
}
