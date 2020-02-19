/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LED.LED_MODE;

public class LEDOff extends InstantCommand {
  private LED _led;
  /**
   * Creates a new LEDOff.
   */
  public LEDOff(LED led) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(led);
    _led = led;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    _led.setMode(LED_MODE.Off);
  }
}
