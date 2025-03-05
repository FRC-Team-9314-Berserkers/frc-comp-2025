package frc.robot.actions;

import frc.robot.Robot;

public enum Actions {
    //Shooter Actions

    //Driver Actions
    setLeftDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setLeftDrive(x);})), // Set the left drive speed
    setRightDriveSpeed (new AnalogAction((Float x) -> {return Robot.driver.setRightDrive(x);})), // Set the right drive speed
    quickStop (new ButtonAction(() -> {return Robot.driver.quickStop();}, () -> {return Robot.driver.quickStopRelease();})), // Quick stop the robot
    
    //Lifter Actions

    //Loader Actions

    
    //Camera Actions
    cameraLeft (new ButtonAction(() -> {Robot.vision.backCameraMove(-1); return true;})), // Move the camera left
    cameraRight (new ButtonAction(() -> {Robot.vision.backCameraMove(1); return true;})), // Move the camera right

    /* QUICK GUIDE: 
         Name:   |       Ignore this      |   What it does:        |  Also ignore!          
    ^^^^^^^^^^^^^                          ^^^^^^^^^^^^^^^^^^^^^^^^  
    - Make sure to use a unique name.
    - Also "what it does" is important to change:
         - return true (or false, it doesn't really matter (yet))
         - Probably something like Robot.lifter.liftArmDown();

    Also, is the loader in Robot.java as a Robot memeber variable?
        (It is, I checked)

    */
    
    templateAction1 (new ButtonAction(() -> {/* Do something here; */ return true;})),
    templateAction2 (new ButtonAction(() -> {/* Do something here; */ return true;}));

    public final Action value;
    Actions(Action value) {
        this.value = value; // Set the value of the enum to the value of the action
    }
}