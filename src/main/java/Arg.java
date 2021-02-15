import java.io.File;
import java.util.ArrayList;

public class Arg {

    private final String[] args;

    private ArrayList<String> errors = new ArrayList<String>();

    public Arg(String[] args) {
        this.args = args;
    }

    private void validation() {
        if (args[0] == null) {
            errors.add("No file way");
        }
        if (!args[1].equals("-u")) {
            errors.add("No usage key");
        }
        if (args[2] == null) {
            errors.add("No usage");
        }
        if (!args[3].equals("-t")) {
            errors.add("No time key");
        }
        if (args[4] == null) {
            errors.add("No time");
        }
    }

    public boolean isValid() {
        validation();
        return errors.size() == 0;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public double usage() {
        return Double.parseDouble(args[2]);
    }

    public double time() {
        return Double.parseDouble(args[4]);
    }

    public File file() {
        return new File(args[0]);
    }
}
