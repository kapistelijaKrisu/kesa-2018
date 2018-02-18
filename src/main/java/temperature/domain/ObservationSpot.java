
package temperature.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ObservationSpot extends AbstractPersistable<Long> {
    private String name;
}
