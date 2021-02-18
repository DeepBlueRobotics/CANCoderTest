package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
/**
 * A class that stores all the variables and methods applicaple to a single swerve module,
 * such as moving, getting encoder values, or configuring PID.
 */
public class SwerveModule extends SubsystemBase {
    //private WPI_TalonSRX turn;
    private CANCoder canCoder;
    private PIDController turnController;
    // position is used for simulation testing
    private double turnZero, pidOutput, position;

    public SwerveModule(/*WPI_TalonSRX turn,*/ CANCoder canCoder, double turnZero) {
        //this.turn = turn;
        canCoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);
        this.canCoder = canCoder;
        this.turnZero = turnZero;

        // kP determined through simulation testing
        turnController = new PIDController(0.01, 0.0, 0.0);
        // Accept 0.1 degrees of tolerance
        turnController.setTolerance(0.1);
        position = 0.0;
    }

    @Override
    public void periodic() {
        if (!turnController.atSetpoint()) {
            if (RobotBase.isSimulation()) {
                // Use the last pidOutput, max speed of NEO, and dt to estimate the change in position
                position += pidOutput * 2724.48 * 0.02;
                pidOutput = turnController.calculate(position);
            }
            else pidOutput = turnController.calculate(canCoder.getPosition());
            pidOutput = MathUtil.clamp(pidOutput, -1.0, 1.0);
            //turn.set(pidOutput);
            SmartDashboard.putNumber("PID Output", pidOutput);
        }
        if (RobotBase.isSimulation()) SmartDashboard.putNumber("Module Angle", position);
        else SmartDashboard.putNumber("Module Angle", canCoder.getPosition());
    }

    public void setAngle(double angle) {
        turnController.setSetpoint(angle);
        SmartDashboard.putNumber("Target Angle", angle);
    }

    /**
     * HomeAbsolute is an instant command that ensures that each of the turn motor controllers are in a known configuration,
     * as dictated by the absolute encoder positions turnZero.
     */
    public void homeAbsolute() {
        // Set the position of the quadrature encoder to the position measured by the CANCoder 
        // relative to the zeroed absolute position (in revolutions)
        canCoder.setPosition(canCoder.getAbsolutePosition() - turnZero);
        // Ensure that we turn to this angle
        setAngle(0.0);
    }
}
