package sunposition.springday.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "date")
    private LocalDate dateOfSunriseSunset;

    @Column(name = "sunrise")
    private LocalTime timeOfSunrise;

    @Column(name = "sunset")
    private LocalTime timeOfSunset;

    @Column(name = "weather_conditions")
    private String weatherConditions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Override
    public String toString() {
        return "Day{"
                + "id=" + id
                + ", location='" + location + '\''
                + ", coordinates='" + coordinates + '\''
                + ", dateOfSunriseSunset=" + dateOfSunriseSunset
                + ", timeOfSunrise=" + timeOfSunrise
                + ", timeOfSunset=" + timeOfSunset
                + ", weatherConditions='" + weatherConditions + '\''
                + '}';
    }
}
