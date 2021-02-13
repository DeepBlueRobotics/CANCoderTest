package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;

public class TeleopDrive extends CommandBase {
    private SwerveModule module;
    private Joystick joy;

    public TeleopDrive(SwerveModule module, Joystick joy) {
        addRequirements(this.module = module);
        this.joy = joy;
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Set the angle of the swerve module to the angle of the joystick for easy control
        module.setAngle(Math.atan2(-joy.getY(), joy.getX()));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
