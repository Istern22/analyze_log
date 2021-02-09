import java.time.LocalTime;

public class Interval {

    private LocalTime start;
    private LocalTime end;
    private double access;

    public Interval(LocalTime start, LocalTime end, double access) {
        this.start = start;
        this.end = end;
        this.access = access;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start +
                ", end=" + end +
                ", access=" + access +
                '}';
    }
}
