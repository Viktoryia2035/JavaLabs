package sunposition.springday.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DayDto {
    private Long id;
    private String location;
    private String coordinates;
    private LocalDate dateOfSunriseSunset;
    private LocalTime timeOfSunrise;
    private LocalTime timeOfSunset;
}