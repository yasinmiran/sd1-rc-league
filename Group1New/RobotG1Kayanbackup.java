package RoboGroup1;

import java.awt.Color;
import robocode.*;
import robocode.util.Utils;

public class RobotG1Kayanbackup extends AdvancedRobot {

	//Variable that will be used to set how much the gun will be turned
	double gunTurn; //Kayan added

	public void run() {

		//###### Robot Colour ######
		setBodyColor(Color.black);
		setGunColor(Color.red);
		setRadarColor(Color.white);
		setScanColor(Color.green);
		setBulletColor(Color.blue);

		//This will prevent the gun to turn with the robot, with it the robot
		//movement and the gun movement are independent of each other
		setAdjustGunForRobotTurn(true);	//Kayan added

		//Will define that the gunTurn Value is 10
		gunTurn = 100; //Kayan added

		while (true) {

			//Will turn the gun to the right, based on gunTurn value
			setTurnGunRight(gunTurn);
			execute();;

		}
	}

	public void onBulletHit(BulletHitEvent e) {

	}
	public void onHitByBullet(BulletHitBulletEvent e) {

	}
	// ****** Muhannad ******
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

	// ****** Ifeoma ******
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

	// ****** Kayan ******
	
	/**
	 * This method is called when the robot's radar detects an enemy robot.
	 * It uses a variety of tactics to attack the enemy, depending on its 
	 * distance.
	 * @param e The ScannedRobotEvent that triggered this method
	 */
	public void onScannedRobot(ScannedRobotEvent e) {

		// Check the distance to the enemy robot 
		if (e.getDistance() > 150) {
			// Calculate the relative angle between the robot and the enemy 
			//robot, taking into account the current heading of the robot and 
			//the current heading of its radar
			gunTurn = relativeAngle(e.getBearing() 
									+ (getHeading() 
									- getRadarHeading()));
			// Turn the gun towards the enemy based on the relative angle 
			//calculation
			setTurnGunRight(gunTurn);
			// Turn the robot towards the enemy
			setTurnRight(e.getBearing());
			// Move the robot closer to the enemy
			setAhead(e.getDistance() - 140);
			// Fire with appropriate power based on distance
			if(e.getDistance() < 200) {
				fire(2);
			}
			else if(e.getDistance() < 500) {
				fire(1);
			}
			else {
				fire(0.5);
			}
			return;
		}
		
//		If robot is closer enough will shoot with maximum power
		gunTurn = relativeAngle(e.getBearing() 
								+ (getHeading() 
								- getRadarHeading()));

		setTurnGunRight(gunTurn);
		setFire(3);
		
		// If the enemy is very close, move backwards
		if (e.getDistance() < 100)
		{
			//will always try to keep a distance of 50
			if (e.getBearing() > -90 && e.getBearing() <= 90)
				setBack(40);
			else
				setAhead(40);
		}
		scan();

	}
	//will return the fixed angle, used on the onScannedRobot()
	public double relativeAngle(double angle) {
		if (angle > -180 && angle <= 180)
			return angle;
		double fixedAngle = angle;
		while (fixedAngle <= -180)
			fixedAngle += 360;
		while (fixedAngle > 180)
			fixedAngle -= 360;
		return fixedAngle;
	}
	// ****** Kayan ******
}



