/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LED.LED_MODE;

public class AutoDriveForward extends CommandBase {
  private DriveTrain _driveTrain;
  private LED _led;

  /**
   * Creates a new AutoDriveForward.
   */
  public AutoDriveForward(DriveTrain driveTrain, LED led) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);
    _driveTrain = driveTrain;

    addRequirements(led);
    _led = led;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    _driveTrain.moveForward();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    _led.setMode(LED_MODE.Rainbow);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double position = _driveTrain.getDrivePosition();
    return (position <= -100);
  }
}
