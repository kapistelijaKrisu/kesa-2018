
package temperature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import temperature.domain.ObservationSpot;

public interface ObservationSpotRepository extends JpaRepository<ObservationSpot, Long> {

}
