package frc.robot;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

public class cam {
    int moterPort = 56;
    PhotonCamera camera = new PhotonCamera("photonvision");
    PhotonTrackedTarget target = camera.getBestTarget();
    double yaw = target.getYaw();
    double pitch = target.getPitch();
    double area = target.getArea();
    double skew = target.getSkew();
}
