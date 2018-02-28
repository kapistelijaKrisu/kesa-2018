package temperature.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import temperature.domain.Location;
import temperature.domain.Observation;
import temperature.repository.LocationRepository;
import temperature.repository.ObservationRepository;

@Service
public class DatabaseInit {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ObservationRepository observationRepository;

    @PostConstruct
    public void init() {
        if (locationRepository.count() != 0) {
            return;
        }
        Location tokio = new Location("Tokio", 35.6584421, 139.7328635, TimeZone.getTimeZone("Asia/Tokyo"), null);
        Location helsinki = new Location("Helsinki", 60.1697530, 24.9490830, TimeZone.getTimeZone("Europe/Helsinki"), null);
        Location newYork = new Location("New York", 40.7406905, -73.9938438, TimeZone.getTimeZone("America/New_York"), null);
        Location amsterdam = new Location("Amsterdam", 52.3650691, 4.9040238, TimeZone.getTimeZone("Europe/Amsterdam"), null);
        Location dubai = new Location("Dubai", 25.092535, 55.1562243, TimeZone.getTimeZone("Asia/Dubai"), null);

        locationRepository.save(tokio);
        locationRepository.save(helsinki);
        locationRepository.save(newYork);
        locationRepository.save(amsterdam);
        locationRepository.save(dubai);

        ArrayList<Location> a = new ArrayList<>();
        a.add(dubai);
        a.add(amsterdam);
        a.add(newYork);
        a.add(helsinki);
        a.add(tokio);
        Random r = new Random();
        a.stream().map((location) -> {
            ArrayList<Observation> obs = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                int temp = r.nextInt(60) - 30;
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, r.nextInt(400000) * -1);

                obs.add(new Observation(location, temp, c));
            }
            return obs;
        }).forEachOrdered((obs) -> {
            observationRepository.save(obs);
        });
    }
}
