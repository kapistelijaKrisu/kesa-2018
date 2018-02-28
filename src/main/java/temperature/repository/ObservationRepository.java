package temperature.repository;

import java.util.Calendar;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import temperature.domain.Location;
import temperature.domain.Observation;

public interface ObservationRepository extends JpaRepository<Observation, Long> {

    public List<Observation> findByLocation(Location location, Pageable page);

    Long countByLocation(Location location);

    public List<Observation> findByLocationAndObservationTimeBetween(Location location, Calendar from, Calendar until, Pageable page);

    public long countByLocationAndObservationTimeBetween(Location location, Calendar from, Calendar until);
}
