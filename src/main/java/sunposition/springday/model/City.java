package sunposition.springday.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "city")

public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private List<Day> days;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "city_country",
            joinColumns = {@JoinColumn(name = "city_id")},
            inverseJoinColumns = {@JoinColumn(name = "country_id")})
    private List<Country> countries;
}