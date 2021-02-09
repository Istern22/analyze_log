import java.time.LocalTime;
import java.util.Objects;

public class Refuse {

    private LocalTime start;
    private LocalTime end;
    private double duration;
    private double access;

    public Refuse(LocalTime start, double duration) {
        this.start = start;
        this.duration = duration;
    }

    public LocalTime getStart() {
        return start;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Refuse refuse = (Refuse) o;
        return Double.compare(refuse.duration, duration) == 0 && start.equals(refuse.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, duration);
    }

    @Override
    public String toString() {
        return "Refuse{" +
                "start=" + start +
                ", duration=" + duration +
                '}';
    }
}
