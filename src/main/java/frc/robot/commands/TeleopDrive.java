package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;

public class TeleopDrive extends CommandBase {
    private SwerveModule module;
    private Joystick joy;
    private double output;

    public TeleopDrive(SwerveModule module, Joystick joy) {
        addRequirements(this.module = module);
        this.joy = joy;
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double joyX = joy.getX();
        double joyY = -joy.getY();

        SmartDashboard.putNumber("Joystick Y", joyY);
        SmartDashboard.putNumber("Joystick X", joyX);

        // Set the angle of the swerve module to the angle of the joystick for easy control
        output = 180 * Math.atan2(joyY, joyX) / Math.PI;
        output = (output < 0) ? 360 + output : output;
        module.setAngle(output);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
