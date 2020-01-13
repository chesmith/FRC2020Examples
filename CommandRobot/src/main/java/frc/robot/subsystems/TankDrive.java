/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class TankDrive extends SubsystemBase {
  private WPI_TalonSRX _frontLeftTalon = new WPI_TalonSRX(DriveConstants.frontLeftTalon);
  private WPI_TalonSRX _frontRightTalon = new WPI_TalonSRX(DriveConstants.frontRightTalon);

  private DifferentialDrive _drive = new DifferentialDrive(_frontLeftTalon, _frontRightTalon);

  private final double _deadband = 0.1;

  public TankDrive() {
    _drive.setDeadband(_deadband);
  }

  public void teleopDrive(Joystick joystick) {
    double forward = joystick.getRawAxis(1);
    double turn = joystick.getRawAxis(4);

    _drive.arcadeDrive(forward, turn);
  }
}
