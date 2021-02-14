import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {

    private static String log;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String x;
        while( (x = input.readLine()) != null ) {
            log = log + x + "\n";
        }
        Arg arguments = new Arg(args);
        if (!arguments.isValid()) {
            for (String error : arguments.getErrors()) {
                System.out.println(error);
            }
            return;
        }
        Scan scan = new Scan(log, arguments.usage(), arguments.time());
        scan.analyze();
        scan.toIntervals();
        scan.convert();
        scan.getResult().stream().forEach(System.out::println);
    }
}
