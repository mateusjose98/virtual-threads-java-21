package sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void ioIntensive(int i) {
        try {
            log.info("início: I/O operation task {}", i);
            Thread.sleep(Duration.ofSeconds(60));
            log.info("final: I/O operation task {}", i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
