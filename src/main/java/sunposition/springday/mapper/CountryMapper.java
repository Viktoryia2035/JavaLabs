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
        if (country == null) {
            return "Country{null}";
        }
        int daysCount = country.getDays().size();
        return "Country{" +
                "name='" + country.getName() + '\'' +
                ", capital='" + country.getCapital() + '\'' +
                ", population=" + country.getPopulation() +
                ", language='" + country.getLanguage() + '\'' +
                ", daysCount=" + daysCount +
                '}';
    }
}
