package sunposition.springday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunposition.springday.model.City;


@Repository

public interface InMemoryCityDAO extends JpaRepository<City, Long>{
    City findByName(String name);
}