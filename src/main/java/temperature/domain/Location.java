package temperature.domain;

import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Location extends AbstractPersistable<Long> {

    private String name;
    private double x;
    private double y;
    private TimeZone timezone;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<Observation> observations;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location wat = (Location) o;
        return this.getId() != null && wat.getId() != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

}
