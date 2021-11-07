import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import java.util.Scanner;

public class VacuumCleaner {
    public static void main(String[] args) {

        Simulator robot = new Simulator();

        String filename = "fcls/control.fcl";
        FIS fis = FIS.load(filename, true);
        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }

        while (true) {
            // default function block
            FunctionBlock fb = fis.getFunctionBlock(null);

            // set the inputs
            fb.setVariable("leftSensor", robot.getDistanceL());
            fb.setVariable("centerSensor", robot.getDistanceC());
            fb.setVariable("rightSensor", robot.getDistanceR());
            // evaluate
            fb.evaluate();

            // show output variable's chart
            fb.getVariable("action").defuzzify();
            double newAngle = fb.getVariable("action").getValue();

            robot.setRobotAngle(newAngle);
            robot.step();
        }
    }
}
