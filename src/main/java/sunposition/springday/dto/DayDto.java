package sunposition.springday.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayDto {
    private Long id;
    private String location;
    private String coordinates;
    private LocalDate dateOfSunriseSunset;
    private LocalTime timeOfSunrise;
    private LocalTime timeOfSunset;
    private String weatherConditions;

    public LocalDate getDate() {
        return dateOfSunriseSunset;
    }
}
