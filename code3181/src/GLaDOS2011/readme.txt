//------------$*$*$*$*$*$*$*$*FOR PROGRAMMERS*$*$*$*$*$*$*$*$------------//

Note: if you have access to Javadocs, read those for a more in-depth explanation
of our classes, methods, and fields.

A quick class summary:
==Package GLaDOS2011==
Arm: Controls the arm
Claw: Controls the claw
DSOutput: Allows output of text to the driver station
DriveSystem: Controls the motors that drive the robot
EnhancedIO: Allows us to get input from the driver station
GLaDOS2011: Our main class, a subclass of IterativeRobot
Hardware: Contains all declarations of hardware, like motors (and more!)
Lifter: Controls the lifter
Minibot: Controls minibot deployment
PANJaguar: A wrapper class for our Jaguars that allows us to use CAN

==Package autono==
See diagram.txt for a picture of what Autono0-Autono4 do.
Sensors: Allows reading of the tape sensors

==Package util==
FileActions: Allows us to read and write files on the cRIO for logging data
Utils: Contains useful methods that don't fit elsewhere


Things to know:

1. Many of our classes have only static fields and methods. These classes are:
    Arm, Claw, EnhancedIO, Hardware, Lifter, Minibot.
    The autonomous classes (located in GLaDOS2011.autono) also fall into this
    subset, but you shouldn't be trying to access these anyway.
    DO NOT CREATE INSTANCES OF THESE CLASSES!!!

2. All of our hardware declarations (and more!) are in one class: Hardware. This
   class contains static declarations only, so you should access them as such.

3. If you change any buttons or print anything to any line of DSOutput, CHANGE
   NOTES.TXT!!! This file makes it easier for us to know what we are doing.


How to do stuff (variables you'll have to name are in **):

1. To drive:
   Hardware.drive.driveAtSpeed(*leftSpeed*, *rightSpeed*);
   -1.0 <= whicheverSpeed <= 1.0

2. To print messages:
   Hardware.txtout.say(*lineNumber*, *message*);
   lineNumber = 1 is the top line, lineNumber = 6 is the bottom line

3. To set a motor directly:
   Hardware.*motorName*.set(*speed*);
   -1.0 <= speed <= 1.0

4. To check whether a joystick button is pushed:
   Hardware.checkButton(*buttonNum* [, *whichJoystick*]);
   1 <= buttonNum <= 11
   Optional parameter whichJoystick = Hardware.LEFT or Hardware.RIGHT;
   Note that button 1 is the trigger.

If you wish to add code, LOOK FOR EXAMPLES AS WELL AS READING THIS. You should
use examples in our code as models for new stuff.



A final warning: before you change anything, MAKE SURE YOU UNDERSTAND THE
CONSEQUENCES OF YOUR ACTIONS. Understand exactly what is happening.




//------------$*$*$*$*$*$*$*$*FOR DRIVERS*$*$*$*$*$*$*$*$------------//

First of all, READ NOTES.TXT!!! This file tells you EXACTLY what every joystick
button and every line of text on the driver station does. This is IMPORTANT.

We are using tank drive. The left joystick controls the left motors, the right
joystick controls the right motors.

More to come...