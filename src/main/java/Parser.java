import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    public static void main(String[] args) throws IOException {
        Arg arguments = new Arg(args);
        if (!arguments.isValid()) {
            for (String error : arguments.getErrors()) {
                System.out.println(error);
            }
            return;
        }
        Scan scan = new Scan(arguments.file(), arguments.usage(), arguments.time());
        scan.analyze();
        scan.toIntervals();
        scan.convert();
        scan.getResult().stream().forEach(System.out::println);
    }
}
