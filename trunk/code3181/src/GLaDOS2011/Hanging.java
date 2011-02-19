package GLaDOS2011;

/**
 * Contains peg heights as constants, as we can't use enums.
 * @author Ben
 */
public class Hanging {
    // Peg heights
    public static final double FLOOR = 0.0;
    public static final double BOTTOM = 2.5;
    public static final double MIDDLE = 5.0;
    public static final double TOP = 7.5;
    public static final double CENTER_OFFSET = .3;

    // Shape constants
    public static final int TRIANGLE = 0;
    public static final int CIRCLE = 1;
    public static final int SQUARE = 2;

    // Shape mode
    public static int mode = 0;

    /**
     * Updates the shape mode for hanging
     */
    public static void updateMode() {
        if(EnhancedIO.getBoxButton(4))
            mode = TRIANGLE;
        else if(EnhancedIO.getBoxButton(5))
            mode = CIRCLE;
        else if(EnhancedIO.getBoxButton(6))
            mode = SQUARE;
    }
}
