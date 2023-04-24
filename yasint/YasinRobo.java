package yasint;

import robocode.*;

public class YasinRobo extends Robot {

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(1);
        turnLeft(90);
        back(10);
        turnRight(180);
        ahead(50);
        super.onScannedRobot(event);
    }

    @Override
    public void run() {
        super.run();
    }

}
