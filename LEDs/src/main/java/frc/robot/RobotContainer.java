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
import frc.robot.Constants.JoystickConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final DriveTrain _driveTrain = new DriveTrain();
  private final Joystick _joystick = new Joystick(0);
  private final LED _led = new LED();

  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final AutoDriveForward m_autoCommand = new AutoDriveForward(_driveTrain, _led);

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
    final JoystickButton _buttonLedRainbow = new JoystickButton(_joystick, JoystickConstants.X);
    _buttonLedRainbow.whenPressed(new LEDRainbow(_led));
    _buttonLedRainbow.whenReleased(new LEDOff(_led));

    final JoystickButton _buttonLedReverseRainbow = new JoystickButton(_joystick, JoystickConstants.B);
    _buttonLedReverseRainbow.whenPressed(new LEDReverseRainbow(_led));

    final JoystickButton _buttonLedPlaid = new JoystickButton(_joystick, JoystickConstants.Y);
    _buttonLedPlaid.whenPressed(new LEDPlaid(_led));

    final JoystickButton _buttonLedOff = new JoystickButton(_joystick, JoystickConstants.A);
    _buttonLedOff.whenPressed(new LEDOff(_led));

    new JoystickButton(_joystick, JoystickConstants.BUMPER_LEFT).whenPressed(new ResetEncoder(_driveTrain));
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
