package aliRobot_Package;

import java.awt.Color;
import robocode.*;
import robocode.util.Utils;

public class aliRobot extends Robot {


	public void run() {

		setBodyColor(Color.black);
		setGunColor(Color.red);
		setRadarColor(Color.white);
		setScanColor(Color.green);
		setBulletColor(Color.blue);

		while (true) {
			ahead(200);
			turnLeft(90);
			ahead(80);
			turnGunRight(180);
			turnRight(90);
			back(90);
			turnGunRight(180);
		}
	}

	public void onBulletHit(BulletHitEvent e) {

	}
	public void onHitByBullet(BulletHitBulletEvent e) {

	}
	
	public void onHitRobot(HitRobotEvent e) {
		// If opponent is at the right of my head, turn to face him
		// If opponent is at left of my head, turn to face him
		double bearing = e.getBearing();
		if (bearing >= 0) {
			turnRight(bearing);
		} else {
			turnLeft(Math.abs(bearing));
		}

		// Shoot depending on energy level
		if (getEnergy() > 25) {
			fire(3);
		} else if (getEnergy() > 15) {
			fire(2);
		} else if (getEnergy() > 0) {
			fire(1);
		}
		// Ram opponent
		ahead(30); 
	}

	public void onHitWall(HitWallEvent e) {

		back(100);

		if (Math.random() > 0.5) {
			turnLeft(90);
		}
		else {
			turnRight(90);
		}

		ahead(100);
	}
	

	public void onScannedRobot(ScannedRobotEvent e) {
		// get the bearing of the enemy 
		double enemy_bearing = getHeading() + e.getBearing();
		//  Normalise the bearing to a value between -180 and 180 degrees
		double normalized_bearing = Utils.normalRelativeAngleDegrees
				(enemy_bearing - getGunHeading());
		// turn the gun to enemy direction
		turnGunRight(normalized_bearing);
		// get the enemy distance of the enemy
		double distance = e.getDistance();
		//shooting options based on the enemy distance
		//for short distances more power, for long distance it will use 
		//less power
		if(distance<200)
		{
			fire(3.5);
		}
		else if(distance<500)
		{
			fire(2.5);
		}
		else if(distance<800)
		{
			fire(1.5);
		}
		else
		{
			fire(0.5);
		}
	}

}