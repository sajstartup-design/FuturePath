package saj.startup.pj.common.util;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component("dateTimeUtil")
public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

    /**
     * Format a Timestamp to "Oct 10, 2025 05:00 PM".
     * Returns "-" if null.
     */
    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return "-";
        }
        LocalDateTime ldt = timestamp.toLocalDateTime();
        return FORMATTER.format(ldt);
    }
}
