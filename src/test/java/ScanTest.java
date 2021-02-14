import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ScanTest {

    @Test
    public void whenSelectAllRefuses() throws IOException {
        File file = new File("./access.log");
        Scan scan = new Scan(file, 99.9, 300);
        scan.analyze();
        ArrayList<Refuse> refuses = scan.getRefuses();
        String result = "";
        for (Refuse refuse : refuses) {
            result = result + refuse.toString() + "\n";
        }
        String expected = "Refuse{start=16:47:04, duration=357.519527}\n" +
                "Refuse{start=16:47:04, duration=371.294008}\n" +
                "Refuse{start=16:47:04, duration=368.424082}\n" +
                "Refuse{start=16:47:04, duration=382.356632}\n" +
                "Refuse{start=16:47:04, duration=366.528659}\n" +
                "Refuse{start=16:47:04, duration=368.253384}\n" +
                "Refuse{start=16:47:04, duration=359.628686}\n" +
                "Refuse{start=16:47:04, duration=381.09072}\n" +
                "Refuse{start=16:47:07, duration=366.414475}\n" +
                "Refuse{start=16:47:21, duration=406.822729}\n" +
                "Refuse{start=16:47:25, duration=331.813462}\n" +
                "Refuse{start=16:47:25, duration=316.649479}\n" +
                "Refuse{start=16:47:25, duration=329.605974}\n" +
                "Refuse{start=16:47:25, duration=308.984096}\n" +
                "Refuse{start=16:47:25, duration=329.047176}\n" +
                "Refuse{start=16:47:25, duration=331.142516}\n" +
                "Refuse{start=16:47:25, duration=327.224867}\n" +
                "Refuse{start=16:47:40, duration=376.488521}\n" +
                "Refuse{start=16:47:51, duration=325.498559}\n" +
                "Refuse{start=16:47:51, duration=308.595062}\n" +
                "Refuse{start=16:47:52, duration=390.552902}\n" +
                "Refuse{start=16:47:52, duration=432.310693}\n" +
                "Refuse{start=16:48:18, duration=332.809272}\n" +
                "Refuse{start=16:48:44, duration=363.992787}\n" +
                "Refuse{start=16:48:44, duration=331.241071}\n";
        assertThat(result, is(expected));
    }

    @Test
    public void whenDivideIntervals() {
        File file = new File("./access.log");
        Scan scan = new Scan(file, 99.9, 300);
        scan.analyze();
        scan.toIntervals();
        ArrayList<ArrayList<Refuse>> intervals = scan.getIntervals();
        String result = "";
        for (ArrayList<Refuse> interval : intervals) {
            for (Refuse refuse : interval) {
                result = result + refuse.toString() + "\n";
            }
            result = result + "\n";
        }
        String expected = "Refuse{start=16:47:04, duration=357.519527}\n" +
                "Refuse{start=16:47:04, duration=371.294008}\n" +
                "Refuse{start=16:47:04, duration=368.424082}\n" +
                "Refuse{start=16:47:04, duration=382.356632}\n" +
                "Refuse{start=16:47:04, duration=366.528659}\n" +
                "Refuse{start=16:47:04, duration=368.253384}\n" +
                "Refuse{start=16:47:04, duration=359.628686}\n" +
                "Refuse{start=16:47:04, duration=381.09072}\n" + "\n" +
                "Refuse{start=16:47:07, duration=366.414475}\n" + "\n" +
                "Refuse{start=16:47:21, duration=406.822729}\n" + "\n" +
                "Refuse{start=16:47:25, duration=331.813462}\n" +
                "Refuse{start=16:47:25, duration=316.649479}\n" +
                "Refuse{start=16:47:25, duration=329.605974}\n" +
                "Refuse{start=16:47:25, duration=308.984096}\n" +
                "Refuse{start=16:47:25, duration=329.047176}\n" +
                "Refuse{start=16:47:25, duration=331.142516}\n" +
                "Refuse{start=16:47:25, duration=327.224867}\n" + "\n" +
                "Refuse{start=16:47:40, duration=376.488521}\n" + "\n" +
                "Refuse{start=16:47:51, duration=325.498559}\n" +
                "Refuse{start=16:47:51, duration=308.595062}\n" + "\n" +
                "Refuse{start=16:47:52, duration=390.552902}\n" +
                "Refuse{start=16:47:52, duration=432.310693}\n" + "\n" +
                "Refuse{start=16:48:18, duration=332.809272}\n" + "\n" +
                "Refuse{start=16:48:44, duration=363.992787}\n" +
                "Refuse{start=16:48:44, duration=331.241071}\n\n";
        assertThat(result, is(expected));
    }

    @Test
    public void whenDivideIntervals2() {
       String str = "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=95f5d9cd HTTP/1.1\" 200 2 11.379104 \"-\" \"@list-item-updater\" prio:0\n" +
               "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=35f8318b HTTP/1.1\" 200 2 19.076753 \"-\" \"@list-item-updater\" prio:0\n" +
               "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=d64617f3 HTTP/1.1\" 200 2 151.23464 \"-\" \"@list-item-updater\" prio:0\n" +
               "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=58225626 HTTP/1.1\" 200 2 162.307081 \"-\" \"@list-item-updater\" prio:0\n" +
               "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6616f299 HTTP/1.1\" 200 2 153.729479 \"-\" \"@list-item-updater\" prio:0\n" +
               "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=c71af7ce HTTP/1.1\" 200 2 139.768954 \"-\" \"@list-item-updater\" prio:0\n" +
               "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=411d81f HTTP/1.1\" 200 2 169.631147 \"-\" \"@list-item-updater\" prio:0\n";
        Scan scan = new Scan(str, 99.9, 150);
        scan.analyze();
        scan.toIntervals();
        ArrayList<ArrayList<Refuse>> intervals = scan.getIntervals();
        String result = "";
        for (ArrayList<Refuse> interval : intervals) {
            for (Refuse refuse : interval) {
                result = result + refuse.toString() + "\n";
            }
            result = result + "\n";
        }
        String expected = "Refuse{start=16:48:46, duration=151.23464}\n" +
                "Refuse{start=16:48:46, duration=162.307081}\n" +
                "Refuse{start=16:48:46, duration=153.729479}\n" +
                "Refuse{start=16:48:46, duration=169.631147}\n\n";
        assertThat(result, is(expected));
    }

    @Test
    public void whenConvert() {
        File file = new File("./access.log");
        Scan scan = new Scan(file, 99.9, 300);
        scan.analyze();
        scan.toIntervals();
        scan.convert();
        String result = "";
        for (Interval interval : scan.getResult()) {
            result = result + interval.toString() + "\n";
        }
        String expected = "Interval{start=16:47:04, end=16:47:04.382, access=99.618}\n" +
                "Interval{start=16:47:07, end=16:47:07.366, access=99.634}\n" +
                "Interval{start=16:47:21, end=16:47:21.406, access=99.59400000000001}\n" +
                "Interval{start=16:47:25, end=16:47:25.331, access=99.669}\n" +
                "Interval{start=16:47:40, end=16:47:40.376, access=99.624}\n" +
                "Interval{start=16:47:51, end=16:47:51.325, access=99.675}\n" +
                "Interval{start=16:47:52, end=16:47:52.432, access=99.568}\n" +
                "Interval{start=16:48:18, end=16:48:18.332, access=99.668}\n" +
                "Interval{start=16:48:44, end=16:48:44.363, access=99.637}\n";
        assertThat(result, is(expected));
    }

    /*@Test
    public void whenConvert2() {
        String str = "192.168.32.181 - - [14/06/2017:16:48:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=95f5d9cd HTTP/1.1\" 200 2 1500 \"-\" \"@list-item-updater\" prio:0\n" +
                "192.168.32.181 - - [14/06/2017:16:48:59 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=35f8318b HTTP/1.1\" 200 2 968 \"-\" \"@list-item-updater\" prio:0\n" +
                "192.168.32.181 - - [14/06/2017:16:49:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=d64617f3 HTTP/1.1\" 200 2 150 \"-\" \"@list-item-updater\" prio:0\n" +
                "192.168.32.181 - - [14/06/2017:16:40:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=58225626 HTTP/1.1\" 200 2 162.307081 \"-\" \"@list-item-updater\" prio:0\n" +
                "192.168.32.181 - - [14/06/2017:16:53:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6616f299 HTTP/1.1\" 200 2 153.729479 \"-\" \"@list-item-updater\" prio:0\n" +
                "192.168.32.181 - - [14/06/2017:16:59:46 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=c71af7ce HTTP/1.1\" 500 2 260 \"-\" \"@list-item-updater\" prio:0\n" +
                "192.168.32.181 - - [14/06/2017:16:59:47 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=411d81f HTTP/1.1\" 200 2 6983 \"-\" \"@list-item-updater\" prio:0\n";
        Scan scan = new Scan(str, 99.9, 1500);
        scan.analyze();
        scan.toIntervals();
        scan.convert();
        String result = "";
        for (Interval interval : scan.getResult()) {
            result = result + interval.toString() + "\n";
        }
        String expected = "Interval{start=16:47:04, end=16:47:04.382, access=99.618}\n" +
                "Interval{start=16:47:07, end=16:47:07.366, access=99.634}\n" +
                "Interval{start=16:47:21, end=16:47:21.406, access=99.59400000000001}\n" +
                "Interval{start=16:47:25, end=16:47:25.331, access=99.669}\n" +
                "Interval{start=16:47:40, end=16:47:40.376, access=99.624}\n" +
                "Interval{start=16:47:51, end=16:47:51.325, access=99.675}\n" +
                "Interval{start=16:47:52, end=16:47:52.432, access=99.568}\n" +
                "Interval{start=16:48:18, end=16:48:18.332, access=99.668}\n" +
                "Interval{start=16:48:44, end=16:48:44.363, access=99.637}\n";
        assertThat(result, is(expected));
    }*/
}