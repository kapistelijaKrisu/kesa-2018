package temperature.services;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import temperature.domain.Location;
import temperature.domain.Observation;
import temperature.repository.LocationRepository;
import temperature.repository.ObservationRepository;

@Service
public class LocationHandler {

    @Autowired
    private ObservationRepository observationRepository;
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocation(Long id) {
        return locationRepository.findOne(id);
    }

    @Transactional
    public boolean checkAndSaveObservationToLocation(long locationId, String temperature) {
        try {
            int intTemperature = Integer.parseInt(temperature);
            if (intTemperature > 100 ||intTemperature < -100) {
                throw new IllegalArgumentException("Don't think temperature can go above 100 or below 100 degrees");
            }
            Location location = locationRepository.getOne(locationId);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(location.getTimezone());

            Observation observation = new Observation(location, intTemperature, calendar);
            observationRepository.save(observation);
            
            return true;
        } catch (NullPointerException | IllegalArgumentException e) {
            return false;
        }
    }
}
