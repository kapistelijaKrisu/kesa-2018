package temperature.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Observation extends AbstractPersistable<Long> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Location location;

    private int temperature;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar observationTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Observation wat = (Observation) o;
        return this.getId() != null && wat.getId() != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    public String printDateTime() {
        int year = observationTime.get(Calendar.YEAR);
        int month = observationTime.get(Calendar.MONTH);
        int dayOfMonth = observationTime.get(Calendar.DAY_OF_MONTH);
        int minute = observationTime.get(Calendar.MINUTE);
        int hourOfDay = observationTime.get(Calendar.HOUR_OF_DAY);
        return "" + minute + ':' + hourOfDay + ' ' + dayOfMonth + '-' + month + '-' + year;
    }

    public String getDisplayTime() {
        
        int minute = observationTime.get(Calendar.MINUTE);
        int hourOfDay = observationTime.get(Calendar.HOUR_OF_DAY);
        return TIME_FORMAT.format(observationTime.getTime());
    }

    public String getDisplayDate() {
        int year = observationTime.get(Calendar.YEAR);
        int month = observationTime.get(Calendar.MONTH) + 1;
        int day = observationTime.get(Calendar.DAY_OF_MONTH);
        return DATE_FORMAT.format(observationTime.getTime());
    }
}
