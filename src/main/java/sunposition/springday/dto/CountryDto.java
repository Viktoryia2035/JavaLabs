package sunposition.springday.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private Long id;
    private String name;
    private String capital;
    private Long population;
    private String language;
    private List<DayDto> days;

    public List<DayDto> getDays() {
        return days;
    }

    public void setDays(final List<DayDto> newDays) {
        days = newDays;
    }
}
