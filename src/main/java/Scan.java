import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Scan {

    private File file;
    private double access;
    private final double level;
    private String list;

    private final ArrayList<Refuse> refuses = new ArrayList<>();
    private final ArrayList<ArrayList<Refuse>> intervals = new ArrayList<>();
    private final ArrayList<Interval> result = new ArrayList<>();


    public Scan(File file, double access, double level) {
        this.list = read(file);
        this.access = access;
        this.level = level;
    }

    public Scan(String list, double access, double level) {
        this.list = list;
        this.access = access;
        this.level = level;
    }

    public ArrayList<Refuse> getRefuses() {
        return refuses;
    }

    public ArrayList<ArrayList<Refuse>> getIntervals() {
        return intervals;
    }

    public ArrayList<Interval> getResult() {
        return result;
    }

    public String read(File file) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void analyze() {
        String[] array = list.split("\n");
        for (String str : array) {
            String[] line = str.split(" ");
            LocalTime start = LocalTime.parse(line[3].substring(12));
            int code = Integer.parseInt(line[8]);
            double duration = Double.parseDouble(line[10]);
            if ((code >= 500 && code < 600) || duration >= level) {
                refuses.add(new Refuse(start, duration));
            }
        }
    }

    public void toIntervals() {
        ArrayList<Refuse> interval = new ArrayList<>();
        for (int i = 0; i < refuses.size() - 1; i++) {
            Refuse refuse = refuses.get(i);
            Refuse refuseNext = refuses.get(i + 1);
            LocalTime start = refuse.getStart();
            LocalTime startNext = refuseNext.getStart();
            double duration = refuse.getDuration();
            LocalTime time = start.plusNanos((long)duration * 1000000);
            interval.add(refuse);
            if(startNext.isAfter(time)) {
                intervals.add(interval);
                interval = new ArrayList<>();
            }
            if(i == refuses.size() - 2) {
                if (!startNext.isAfter(time)) {
                    interval.add(refuseNext);
                    intervals.add(interval);
                    interval = new ArrayList<>();
                } else {
                    intervals.add(interval);
                    interval = new ArrayList<>();
                    interval.add(refuseNext);
                    intervals.add(interval);
                }
            }
        }
    }

    public void convert() {
        LocalTime absoluteStart = refuses.get(0).getStart();
        LocalTime absoluteEnd = refuses.get(refuses.size() - 1).getStart();
        LocalTime fullTime = absoluteEnd.minusHours(absoluteStart.getHour())
                .minusMinutes(absoluteStart.getMinute())
                .minusSeconds(absoluteStart.getSecond());
        for (ArrayList<Refuse> interval : intervals) {
            LocalTime fullDuration = interval.get(0).getStart();
            for (Refuse refuse : interval) {
                LocalTime duration = refuse.getStart().plusNanos((long)refuse.getDuration() * 1000000);
                if (fullDuration.isBefore(duration)) {
                    fullDuration = duration;
                }
            }
            LocalTime startInterval = interval.get(0).getStart();
            LocalTime intervalDuration = fullDuration.minusHours(startInterval.getHour())
                    .minusMinutes(startInterval.getMinute())
                    .minusSeconds(startInterval.getSecond());
            double intervalNano = intervalDuration.toNanoOfDay();
            double fullNano = fullTime.toNanoOfDay();
            double accessInterval = (1 - intervalNano / fullNano) * 100;
            result.add(new Interval(interval.get(0).getStart(), fullDuration, accessInterval));
        }
    }
}
