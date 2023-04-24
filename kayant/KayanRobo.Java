package kayant;

import robocode.*;
import java.awt.Color;
import robocode.util.Utils;

public class KayanRobo extends Robot {

    private boolean gunTurning = false;

    private boolean movingLeft = false;

    public void run() {
        // Set robot colors
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.black);
        setScanColor(Color.black);

        while (true) {
            // move left or right depending on the current direction
            if (movingLeft) {
                turnLeft(180);
                ahead(100);
            } else {
                turnRight(180);
                ahead(100);
            }
            // switch direction after moving
            movingLeft = !movingLeft;
            // scan for enemies
            turnGunRight(360);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // get the absolute bearing to the enemy
        double enemyBearing = getHeading() + e.getBearing();
        // normalize the bearing to a value between -180 and 180 degrees
        double normalizedBearing = Utils.normalRelativeAngleDegrees(enemyBearing - getGunHeading());
        // turn the gun to face the enemy
        turnGunRight(normalizedBearing);
        // set gunTurning flag to true
        gunTurning = true;
        // fire at the enemy
        double distance = e.getDistance();
        if (distance < 200) {
            fire(3.5);
        } else if (distance < 500) {
            fire(2.5);
        } else if (distance < 800) {
            fire(1.5);
        } else {
            fire(0.5);
        }
    }

    public void onStatus(StatusEvent e) {
        // set gunTurning flag to false when the gun has stopped turning
        if (gunTurning && getGunHeat() == 0) {
            gunTurning = false;
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        // move away from the enemy
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);
        } else {
            ahead(100);
        }
    }

}
