package frc.robot.subsystems;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A class that stores all the variables and methods applicaple to a single swerve module,
 * such as moving, getting encoder values, or configuring PID.
 */
public class SwerveModule extends SubsystemBase {
    private CANSparkMax turn;
    private CANPIDController pidController;
    private CANCoder canCoder;
    private double turnZero;

    public SwerveModule(CANSparkMax turn, CANCoder canCoder, double turnZero) {
        this.turn = turn;
        this.canCoder = canCoder;
        this.turnZero = turnZero;

        canCoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);

        pidController = turn.getPIDController();
        // TODO: Determine better kP, kI, and kD values
        pidController.setP(10.0);
        pidController.setI(0.0);
        pidController.setD(0.0);
    }

    public void setAngle(double angle) {
        pidController.setReference(angle, ControlType.kPosition);
    }

    /**
     * Updates SmartDashboard with information about this module.
     */
    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Module angle", canCoder.getAbsolutePosition());
    }

    /**
     * HomeAbsolute is an instant command that ensures that each of the turn motor controllers are in a known configuration,
     * as dictated by the absolute encoder positions turnZero.
     */
    public void homeAbsolute() {
        // Set the position of the quadrature encoder to the position measured by the CANCoder 
        // relative to the zeroed absolute position (in revolutions)
        turn.getEncoder().setPosition((canCoder.getAbsolutePosition() - turnZero) / (Math.PI * 2));
        // Ensure that we turn to this angle
        setAngle(0.0);
    }
}
