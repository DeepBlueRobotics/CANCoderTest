package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.SwerveModule;

public class HomeAbsolute extends InstantCommand {
    private final SwerveModule module;
  
    public HomeAbsolute(SwerveModule module) {
        this.module = module;
    }
  
    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        module.homeAbsolute();
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
