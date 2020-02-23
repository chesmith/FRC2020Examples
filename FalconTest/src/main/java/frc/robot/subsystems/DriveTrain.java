/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.JoystickConstants;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */

  int MUSICMODE = 0;

  WPI_TalonFX _motorController = new WPI_TalonFX(1);
  FurElise _music = new FurElise();

  Orchestra _orchestra;
  String[] _songs = new String[] {
    "song1.chrp",
    "song2.chrp",
    "song3.chrp",
    "song4.chrp",
    "song5.chrp",
    "song6.chrp",
    "song7.chrp",
    "song8.chrp",
    "song9.chrp", /* the remaining songs play better with three or more FXs */
    "song10.chrp",
    "song11.chrp",
    "song12.chrp",
    "song13.chrp"
  };
  int _songSelection = 0;
  int _timeToPlayLoops = 0;
  int _lastButton = 0;
  int _lastPOV = 0;

  final int kUnitsPerRevolution = 2048; /* this is constant for Talon FX */
  int _loops = 0;

  public DriveTrain() {
    ArrayList<TalonFX> _instruments = new ArrayList<TalonFX>();
    _instruments.add(_motorController);
    _orchestra = new Orchestra(_instruments);

    LoadMusicSelection(12);
  }

  public void teleopDrive(Joystick joystick) {
    if(MUSICMODE == 0) {
      double speed = joystick.getRawAxis(JoystickConstants.LEFT_STICK_Y);
      if (Math.abs(speed) > 0.1)
        _motorController.set(speed);
      else
        _motorController.set(0);
    }
  }

  @Override
  public void periodic() {
    if(MUSICMODE == 0) {
      /* get the selected sensor for PID0 */
      double appliedMotorOutput = _motorController.getMotorOutputPercent();
      int selSenPos = _motorController.getSelectedSensorPosition(0); /* position units */
      int selSenVel = _motorController.getSelectedSensorVelocity(0); /* position units per 100ms */

      /* scaling depending on what user wants */
      double pos_Rotations = (double) selSenPos / kUnitsPerRevolution;
      double vel_RotPerSec = (double) selSenVel / kUnitsPerRevolution * 10; /* scale per100ms to perSecond */
      double vel_RotPerMin = vel_RotPerSec * 60.0;

      /*
      * Print to console. This is also a good oppurtunity to self-test/plot in Tuner
      * to see how the values match.
      * 
      * Note these prints can cause "Loop time of 0.02s overrun" errors in the console.
      * This is because prints are slow.
      */
      if (++_loops >= 10) {
        _loops = 0;
        SmartDashboard.putNumber("Motor-out", appliedMotorOutput);
        SmartDashboard.putNumber("position", selSenPos);
        SmartDashboard.putNumber("velocity:", selSenVel);
        SmartDashboard.putNumber("Pos-Rotations", pos_Rotations);
        SmartDashboard.putNumber("Vel-RPS", vel_RotPerSec);
        SmartDashboard.putNumber("Vel-RPM", vel_RotPerMin);
      }
    }

    if(MUSICMODE == 1) {
      int dt = 20; // 20ms per loop
      double freq = _music.GetMusicFrequency(dt);
      _motorController.set(TalonFXControlMode.MusicTone, freq);
    }

    if(MUSICMODE == 2) {
      if (_timeToPlayLoops > 0) {
        --_timeToPlayLoops;
        if (_timeToPlayLoops == 0) {
            _orchestra.play();
        }
      }
    }
  }

  void LoadMusicSelection(int offset)
  {
      _songSelection += offset;
      if (_songSelection >= _songs.length) {
          _songSelection = 0;
      }
      if (_songSelection < 0) {
          _songSelection = _songs.length - 1;
      }
      _orchestra.loadMusic(_songs[_songSelection]); 

      /* schedule a play request, after a delay.  
          This gives the Orchestra service time to parse chirp file.
          If play() is called immedietely after, you may get an invalid action error code. */
      _timeToPlayLoops = 10;
  }
}
