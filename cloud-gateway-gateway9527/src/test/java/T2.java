import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class T2 {

    @Test
    void ZonedDateTimeTest() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);    //2021-05-15T08:03:46.454+08:00[Asia/Shanghai]
    }
}
