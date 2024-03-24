package sunposition.springday.mapper;

import sunposition.springday.dto.CountryDto;
import sunposition.springday.model.Country;
import sunposition.springday.model.Day;

public final class CountryMapper {

    private CountryMapper() {
    }

    public static CountryDto toDto(final Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        countryDto.setCapital(country.getCapital());
        countryDto.setPopulation(country.getPopulation());
        countryDto.setLanguage(country.getLanguage());
        return countryDto;
    }

    public static Country toEntity(final CountryDto countryDto) {
        Country country = new Country();
        country.setId(countryDto.getId());
        country.setName(countryDto.getName());
        country.setCapital(countryDto.getCapital());
        country.setPopulation(countryDto.getPopulation());
        country.setLanguage(countryDto.getLanguage());
        return country;
    }

    public static String toString(Country country) {
        StringBuilder daysString = new StringBuilder();
        for (Day day : country.getDays()) {
            daysString.append(DayMapper.toString(day)).append(", ");
        }
        if (daysString.length() > 0) {
            daysString.setLength(daysString.length() - 2);
        }

        return "Country{" +
                "name='" + country.getName() + '\'' +
                ", days=" + daysString.toString() +
                '}';
    }
}
