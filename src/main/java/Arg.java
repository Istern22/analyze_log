import java.util.ArrayList;

public class Arg {

    private final String[] args;

    private ArrayList<String> errors = new ArrayList<String>();

    public Arg(String[] args) {
        this.args = args;
    }

    private void validation() {
        if (!args[0].equals("-u")) {
            errors.add("No usage key");
        }
        if (args[1] == null) {
            errors.add("No usage");
        }
        if (!args[2].equals("-t")) {
            errors.add("No time key");
        }
        if (args[3] == null) {
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
        return Double.parseDouble(args[1]);
    }

    public double time() {
        return Double.parseDouble(args[3]);
    }
}
