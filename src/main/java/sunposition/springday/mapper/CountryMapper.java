package sunposition.springday.mapper;

import sunposition.springday.dto.CountryDto;
import sunposition.springday.model.Country;

public class CountryMapper {

    public static CountryDto toDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        countryDto.setCapital(country.getCapital());
        return countryDto;
    }

    public static Country toEntity(CountryDto countryDto) {
        Country country = new Country();
        country.setId(countryDto.getId());
        country.setName(countryDto.getName());
        country.setCapital(countryDto.getCapital());
        return country;
    }
}

