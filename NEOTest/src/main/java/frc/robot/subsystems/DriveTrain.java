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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.JoystickConstants;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */

  CANSparkMax _motorController = new CANSparkMax(1, MotorType.kBrushless);

  public DriveTrain() {

  }

  public void teleopDrive(Joystick joystick) {
    double speed = joystick.getRawAxis(JoystickConstants.LEFT_STICK_Y);
    if (Math.abs(speed) > 0.1)
      _motorController.set(speed);
    else
      _motorController.set(0);
  }

  @Override
  public void periodic() {
      // This method will be called once per scheduler run
  }
}
