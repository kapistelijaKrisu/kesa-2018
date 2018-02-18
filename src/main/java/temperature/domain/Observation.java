
package temperature.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Observation extends AbstractPersistable<Long> {
    
    @ManyToOne
    @JoinColumn  
    private Location location;
    
    private float temperature;
    private LocalDateTime observationTime;
}
