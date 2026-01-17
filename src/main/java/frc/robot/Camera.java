package frc.robot;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Camera extends SubsystemBase{
    private final PhotonCamera m_camera;
    private final SparkMax m_motor;
    private final PIDController m_pid;
    private final RelativeEncoder m_encoder;
    public Camera() {
        m_camera = new PhotonCamera("LifeCam");
        m_motor = new SparkMax(Constants.kMotorPort, MotorType.kBrushless);
        m_pid = new PIDController(0.001, 0, 0);
        m_encoder = m_motor.getEncoder();
        m_pid.enableContinuousInput(0, 360);
    }
    
    @Override
    public void periodic() {
        PhotonTrackedTarget target = m_camera.getAllUnreadResults().get(0).getBestTarget();
        double yaw = target.getYaw();
        m_pid.setSetpoint(yaw);
        double speed = m_pid.calculate(m_encoder.getPosition());
        if(Math.abs(speed) > 0.3) {
            throw new RuntimeException("Speed too high: " + speed);
        }
        m_motor.set(speed);
        //double pitch = target.getPitch();
        //double area = target.getArea();
        //double skew = target.getSkew();
        /*if (camera.getLatestResult().hasTargets()) {
            List<PhotonTrackedTarget> targets = camera.getLatestResult().getTargets();
            for (PhotonTrackedTarget target : targets) {
                System.out.println("Target ID: " + target.getFiducialId());
                System.out.println("Yaw: " + target.getYaw());
                System.out.println("Pitch: " + target.getPitch());
                System.out.println("Area: " + target.getArea());
                System.out.println("Skew: " + target.getSkew());
            }
        } else {
            System.out.println("No targets found.");
        }*/
    }
}
