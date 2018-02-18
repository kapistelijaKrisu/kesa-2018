
package temperature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import temperature.domain.Location;

public interface ObservationSpotRepository extends JpaRepository<Location, Long> {

}
