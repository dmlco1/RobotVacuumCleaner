import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class VacuumCleaner {
    public static void main(String[] args) {

        // initialize Simulator
        Simulator vacuum = new Simulator();

        // get file
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
            fb.setVariable("leftSensor", vacuum.getDistanceL());
            fb.setVariable("centerSensor", vacuum.getDistanceC());
            fb.setVariable("rightSensor", vacuum.getDistanceR());
            // evaluate
            fb.evaluate();

            // show output variable's chart
            fb.getVariable("action").defuzzify();
            double newAngle = fb.getVariable("action").getValue();

            // set the new angle for the vacuum wheels
            vacuum.setRobotAngle(newAngle);
            // step - new state of the GUI
            vacuum.step();
        }
    }
}
