package GLaDOS2011;

import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * Utilities for managing the Driver Station output window.
 * @author Chris Cheng
 * @author Ben
 */
public class DSOutput {
    
    // <editor-fold defaultstate="collapsed" desc="Variables">
    static DriverStationLCD output;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Make a new DSOutput instance.  Not hard.
     */
    public DSOutput() {
        output = DriverStationLCD.getInstance();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void DSOutput.say(int ln, String msg)">
    /**
     * Display a line of text without scrolling.  Max 22 characters / line.
     * @param ln Which line to print in
     * @param msg What message to display
     */
    public void say(int ln, String msg) {
        // If the given message is too long, truncate it
        if (msg.length() > 22) {
            msg = msg.substring(0, 22);
            // If the given message is "", then clear the output line

        } else if (msg.equals("")) {
            msg = "                      ";
            // Determine which line to print on

        }
        switch (ln) {
            case (1):
                output.println(DriverStationLCD.Line.kMain6, 1, msg);
                break;
            case (2):
                output.println(DriverStationLCD.Line.kUser2, 1, msg);
                break;
            case (3):
                output.println(DriverStationLCD.Line.kUser3, 1, msg);
                break;
            case (4):
                output.println(DriverStationLCD.Line.kUser4, 1, msg);
                break;
            case (5):
                output.println(DriverStationLCD.Line.kUser5, 1, msg);
                break;
            case (6):
                output.println(DriverStationLCD.Line.kUser6, 1, msg);
                break;
        }
        // Show the message
        output.updateLCD();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public void DSOutput.clearOutput()">
    /**
     * Clear the entire output box.
     */
    public void clearOutput() {
        say(1, "");
        say(2, "");
        say(3, "");
        say(4, "");
        say(5, "");
        say(6, "");
    }
    // </editor-fold>
}
