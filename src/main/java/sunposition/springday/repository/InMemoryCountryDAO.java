package sunposition.springday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunposition.springday.model.Country;


@Repository
public interface InMemoryCountryDAO extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
