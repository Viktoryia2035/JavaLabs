package sunposition.springday.dto;

import lombok.*;

import java.util.List;

@Data
public class CountryDto {
    private Long id;
    private String name;
    private String capital;
    private List<DayDto> days;

    public List<DayDto> getDays() {
        return days;
    }

    public void setDays(List<DayDto> days) {
        this.days = days;
    }
}
