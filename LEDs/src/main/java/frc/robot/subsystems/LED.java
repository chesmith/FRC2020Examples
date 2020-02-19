/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
  public static enum LED_MODE {
    Off, Rainbow, ReverseRainbow, Plaid
  }

  private enum LED_COLOR {
    Green, Blue, Yellow
  }
  
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;
  private int m_rainbowFirstPixelHue;
  private final int _numLEDs = 102;
  private LED_COLOR _currentColor = LED_COLOR.Blue;
  private double _lastTime = Timer.getFPGATimestamp();

  private LED_MODE _mode = LED_MODE.Off;

  /**
   * Creates a new LEDs.
   */
  public LED() {
    m_led = new AddressableLED(9);
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(_numLEDs);
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      m_ledBuffer.setRGB(i, 0, 0, 0);
    }

    m_led.setData(m_ledBuffer);
  }

  public void setMode(LED_MODE mode) {
    _mode = mode;
  }

  private void off() {
    SmartDashboard.putString("LED Mode", "off");
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      m_ledBuffer.setRGB(i, 0, 0, 0);
    }
  }

  private void rainbow() {
    SmartDashboard.putString("LED Mode", "rainbow");
    // For every pixel
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
  }

  private void reverseRainbow() {
    SmartDashboard.putString("LED Mode", "reverse rainbow");
    // For every pixel
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue -= 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
  }

  private void plaid() {
    SmartDashboard.putString("LED Mode", "plaid");
    double currentTime = Timer.getFPGATimestamp();
    if ((currentTime - _lastTime) > 0.25) {
      _lastTime = currentTime;
      // put your main code here, to run repeatedly:
      for (int i = 0; i < m_ledBuffer.getLength(); i++) {
        if ((i + 1) % 4 == 0) {
          if (_currentColor == LED_COLOR.Blue) {
            _currentColor = LED_COLOR.Green;
          } else if (_currentColor == LED_COLOR.Green) {
            _currentColor = LED_COLOR.Yellow;
          } else if (_currentColor == LED_COLOR.Yellow) {
            _currentColor = LED_COLOR.Blue;
          }
        }

        if (_currentColor == LED_COLOR.Blue) {
          m_ledBuffer.setRGB(i, 0, 102, 255);
        } else if (_currentColor == LED_COLOR.Yellow) {
          m_ledBuffer.setRGB(i, 255, 255, 0);
        } else if (_currentColor == LED_COLOR.Green) {
          m_ledBuffer.setRGB(i, 46, 184, 46);
        }
      }
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    switch(_mode) {
      case Rainbow: rainbow(); break;
      case ReverseRainbow: reverseRainbow(); break;
      case Plaid: plaid(); break;
      default: off(); break;
    }
    m_led.setData(m_ledBuffer);
  }
}
