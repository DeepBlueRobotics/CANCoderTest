// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.SwerveModule;

public class RobotContainer {
  private final Joystick joystick;
  public final SwerveModule module;
  
  public RobotContainer() {
    joystick = new Joystick(Constants.joystickPort);
    module = new SwerveModule(new CANSparkMax(Constants.turnMotorPort, MotorType.kBrushless),
                              new CANCoder(Constants.cancoderPort), Constants.turnZero);
    module.setDefaultCommand(new TeleopDrive(module, joystick));
  }
}
