// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.TimedRobot; // Include FRC TimedRobot files.
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; // Include files for sending and receving data.
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; // Iclude files for smart dashboard
//import frc.robot.actions.Actions; //Include all other project files
import frc.robot.autonomous.AutoAction; //Include all other project files
import frc.robot.autonomous.AutoMode; //Include all other project files
import frc.robot.systems.*; //Include all other project files
import java.math.BigDecimal;
import java.math.RoundingMode;
//import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.SparkBase.PersistMode;
//import com.revrobotics.spark.SparkBase.ResetMode;
//import com.revrobotics.spark.SparkLowLevel.MotorType;
//import com.revrobotics.spark.config.SparkMaxConfig;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default"; // No current purpose
  private static final String kCustomAuto = "My Auto"; // No current purpose
  private String m_autoSelected; // No current purpose
  private final SendableChooser<String> m_chooser = new SendableChooser<>(); // No current purpose
  boolean setAutoMode;
  int AutonomousTime = 50;
  //Drive Motor Controllers now in Driver system

  //Systems
  public static Driver driver; // Initalize the variable for the  driver system
 // public static Shooter shooter; // Initalize the variable for the shooter system
  public static Controller controller; // Initalize the variable for the controller system
 // public static Lifter lifter; // Initalize the variable for the lifter system
 // public static Loader loader; // Initalize the variable for the loader system
  public static Vision vision; // Initalize the variable for the vision system
  public static Elevator elevator; // Initalize the variable for the elevator system

  AutoMode auto1; // Autonomous mode for testing
  private AutoMode autoPrime; // Autonomous mode for driving forward
  private AutoMode autoBack;

  @Override
  public void robotInit() { // This function is called once when the robot is first started up.
    /* 
    m_chooser.setDefaultOption("Forward Auto", kDefaultAuto); // No current purpose
    m_chooser.addOption("Backward Auto", kCustomAuto); // No Current Purpose
    SmartDashboard.putData("Auto choices", m_chooser); // No Current Purpose
  */
  SmartDashboard.putBoolean("Auto Forward", true);
    //Setup Systems
    driver = new Driver(); // Initialize the driver system
   // shooter = new Shooter(); // Initialize the shooter system
    controller = new Controller(); // Initialize the controller system
   // lifter = new Lifter(); // Initialize the lifter system
   // loader = new Loader(); // Initialize the loader system
    vision = new Vision(); // Initialize the vision system
    elevator = new Elevator(); // Initialize the elevator system
    //Set up actions
    //WIP

    vision.start(); // Start camera feed.
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    Util.log("Autonomous started.");
    /*
    m_autoSelected = m_chooser.getSelected();
     m_autoSelected = SmartDashboard.getString("Auto choices", "yay");
     Util.log("AUTO MODE SET ::::::::::::::::::::::::" + m_autoSelected);
     */
    //Util.log("Auto selected: " + m_autoSelected);
     setAutoMode = SmartDashboard.getBoolean("Auto Forward", true);

    auto1 = new AutoMode("auto1"); // Create a new autonomous mode
    auto1.add(new AutoAction(() -> {Util.log("111"); return true;})); // Print "111" to the console
    auto1.add(new AutoAction(() -> {Util.log("222"); return true;})); // Print "222" to the console

    autoPrime = new AutoMode("Auto Drive Forward"); // Create a new autonomous mode
    autoPrime.add(new AutoAction(1.0f, ()->{return true;}, () -> {Robot.driver.straight(0.2f);return true;})); // Makes the robot drive backward for 1.8 seconds at 0.2 speed
    autoPrime.add(new AutoAction(0.2f, () -> {Robot.driver.straight(0.0f);return true;})); // Makes the robot stop driving. Runs for 0.2 seconds.

    autoBack = new AutoMode("Auto Backward");
    autoBack.add(new AutoAction(1.0f, ()->{return true;}, () -> {Robot.driver.straight(-0.2f);return true;}));
    autoBack.add(new AutoAction(0.2f, () -> {Robot.driver.straight(0.0f);return true;}));

    if (!setAutoMode){
      autoPrime.start(); // Initialize the autonomous mode
    } else {
      autoBack.start();
    }
    Util.log(m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        Util.log("One");
        break;
      case kDefaultAuto:
        Util.log("Two");
        break;
      default:
        // Put default auto code here
        //driver.straight(0.2f);
        break;
    }*/
    if (!setAutoMode){
      autoPrime.update(); // Initialize the autonomous mode
    } else {
      autoBack.update();
    }
   // autoPrime.update(); // Update the autonomous mode

    driver.update(); // Update the driver system
   // shooter.update(); // Update the shooter system
   // lifter.update(); // Update the lifter system
   // loader.update(); // Update the loader system
    vision.update(); // Update the vision system
  }


  @Override
  public void teleopInit() {
    Util.log("Teleop started."); // Print "Teleop started." to the console
    //shooter.start();
  }


  @Override
  public void teleopPeriodic() {
    controller.update(); // Update the controller system

    driver.update(); // Update the driver system
   // shooter.update(); // Update the shooter system
   // lifter.update(); // Update the lifter system
   // loader.update(); // Update the loader system
   //elevator.update();
    vision.update(); // Update the vision system
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Util.log("Robot Disabled.");
  //  lifter.stop(); // Stop the lifter system
   // shooter.stop(); // Stop the shooter system
    driver.quickStop();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
   // elevator.test(0.1);
  // elevator.encoderSetZero();
  }

  /** This function is called periodically during test mode. */
  @Override
   public void testPeriodic() {
    
  // Util.log(String.valueOf(Math.round(elevator.getEncoder()* Math.pow(10,2))));
  // elevator.test(0.12);
  controller.update();
 // elevator.update();
  Util.log(String.valueOf(elevator.getEncoder(1)) + ":::::" + String.valueOf(elevator.getEncoder(2)));
    //Util.log(elevator.desiredElev atorPosition);
  } 

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
