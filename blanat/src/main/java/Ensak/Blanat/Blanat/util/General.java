package Ensak.Blanat.Blanat.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class General {

    public static String calculateTimePassed(LocalDateTime creationTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(creationTime, now);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Calculate remaining minutes

        if (hours > 0) {
            if (hours <= 23) {
                // If the difference is less than 23 hours, show in hours
                return hours + "h" + minutes + "min";
            } else {
                // If the difference is more than 23 hours, show in days
                long days = duration.toDays();
                return days + "d";
            }
        } else {
            // If the difference is less than 1 hour, show in minutes
            return minutes + "min";
        }
    }
}
