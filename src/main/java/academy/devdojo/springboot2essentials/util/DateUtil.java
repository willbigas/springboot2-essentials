package academy.devdojo.springboot2essentials.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public String formatLocalDateTimeToDatabaseStyle(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss").format(dateTime);
    }
}
