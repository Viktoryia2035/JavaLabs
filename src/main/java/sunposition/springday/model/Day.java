package sunposition.springday.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
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

    @Column(name = "wea_condition")
    private String weatherConditions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}