/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.JoystickConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.LEDStrip.LED_MODE;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // private final DriveTrain _driveTrain = new DriveTrain();
  private final Joystick _joystick = new Joystick(0);
  private final LEDStrip _led = new LEDStrip(100, 9);

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  // private final AutoDriveForward m_autoCommand = new AutoDriveForward(_driveTrain, _led);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(_joystick, JoystickConstants.X)
      .whenPressed(new LEDRainbow(_led))
      .whenReleased(new LEDOff(_led));

    new JoystickButton(_joystick, JoystickConstants.B)
      .whenPressed(new LEDReverseRainbow(_led));

    new JoystickButton(_joystick, JoystickConstants.Y)
      .whenPressed(new LEDPlaid(_led));

    new JoystickButton(_joystick, JoystickConstants.A)
      .whenPressed(new LEDOff(_led));

    // new JoystickButton(_joystick, JoystickConstants.BUMPER_LEFT)
    //   .whenPressed(new ResetEncoder(_driveTrain));

    new POVButton(_joystick, 0).whenPressed(() -> _led.increaseInterval());
    
    new POVButton(_joystick, 180).whenPressed(() -> _led.decreaseInterval());
  }

  public void disable() {
    _led.setMode(LED_MODE.Off);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
