package sunposition.springday.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capital")
    private String capital;

    @Column(name = "population")
    private Long population;

    @Column(name = "language")
    private String language;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Day> days = new ArrayList<>();

    public LocalTime getSunriseTime() {
        if (days.isEmpty()) {
            return null;
        }

        LocalTime earliestSunrise = LocalTime.MAX;
        for (Day day : days) {
            LocalTime sunriseTime = day.getTimeOfSunrise();
            if (sunriseTime.isBefore(earliestSunrise)) {
                earliestSunrise = sunriseTime;
            }
        }
        return earliestSunrise;
    }
}
