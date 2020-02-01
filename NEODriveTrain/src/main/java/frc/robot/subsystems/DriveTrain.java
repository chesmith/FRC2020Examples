/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.JoystickConstants;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */
  private final CANSparkMax _sparkLeftFront = new CANSparkMax(DriveConstants.k_sparkLeftFront, MotorType.kBrushless);
  private final CANSparkMax _sparkLeftBack = new CANSparkMax(DriveConstants.k_sparkLeftBack, MotorType.kBrushless);
  private final CANSparkMax _sparkRightFront = new CANSparkMax(DriveConstants.k_sparkRightFront, MotorType.kBrushless);
  private final CANSparkMax _sparkRightBack = new CANSparkMax(DriveConstants.k_sparkRightBack, MotorType.kBrushless);

  private final DifferentialDrive _drive = new DifferentialDrive(_sparkLeftFront, _sparkRightFront);

  public DriveTrain() {
  }

  public void configDrive() {
    _sparkLeftBack.follow(_sparkLeftFront);
    _sparkRightBack.follow(_sparkRightFront);
  }

  public void arcadeDrive(Joystick joystick) {
    double forward = joystick.getRawAxis(JoystickConstants.LEFT_STICK_Y);
    double turn = joystick.getRawAxis(JoystickConstants.RIGHT_STICK_X);

    _drive.arcadeDrive(forward, turn);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
