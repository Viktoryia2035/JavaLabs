package sunposition.springday.mapper;

import sunposition.springday.dto.DayDto;
import sunposition.springday.model.Day;

public class DayMapper {
    private DayMapper() {
    }

    public static DayDto toDto(Day day) {
        if (day == null) {
            return null;
        }
        return new DayDto(
                day.getId(),
                day.getLocation(),
                day.getCoordinates(),
                day.getDateOfSunriseSunset(),
                day.getTimeOfSunrise(),
                day.getTimeOfSunset(),
                day.getWeatherConditions()
        );
    }

    public static Day toEntity(DayDto dayDto) {
        if (dayDto == null) {
            return null;
        }
        Day day = new Day();
        day.setId(dayDto.getId());
        day.setLocation(dayDto.getLocation());
        day.setCoordinates(dayDto.getCoordinates());
        day.setDateOfSunriseSunset(dayDto.getDateOfSunriseSunset());
        day.setTimeOfSunrise(dayDto.getTimeOfSunrise());
        day.setTimeOfSunset(dayDto.getTimeOfSunset());
        day.setWeatherConditions(dayDto.getWeatherConditions());
        return day;
    }
}
