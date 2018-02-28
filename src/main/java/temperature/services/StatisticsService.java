package temperature.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import temperature.domain.Location;
import temperature.domain.Observation;
import temperature.repository.ObservationRepository;

@Service
public class StatisticsService {

    @Autowired
    private ObservationRepository observationRepository;

    public Observation coldestIn24h(Location location) {
        Pageable coldest = new PageRequest(0, 1, Sort.Direction.ASC, "temperature");

        List<Observation> within24h = getlast24hObservationsByPageable(location, coldest);

        if (within24h.isEmpty()) {
            return null;
        }
        return within24h.get(0);
    }

    public Observation hottestIn24h(Location location) {
        Pageable hottest = new PageRequest(0, 1, Sort.Direction.DESC, "temperature");
        List<Observation> within24h = getlast24hObservationsByPageable(location, hottest);
        if (within24h.isEmpty()) {
            return null;
        }
        return within24h.get(0);
    }

    public Observation getLatestObservation(Location location) {
        Pageable latest = new PageRequest(0, 1, Sort.Direction.DESC, "observationTime");
        List<Observation> lastObservation = observationRepository.findByLocation(location, latest);
        if (lastObservation.isEmpty()) {
            return null;
        }
        return lastObservation.get(0);
    }

    public Map<Long, Observation> coldestIn24h(Collection<Location> locations) {
        Map<Long, Observation> mappedResults = new HashMap<>();
        locations.forEach((location) -> {
            mappedResults.put(location.getId(), coldestIn24h(location));
        });
        return mappedResults;
    }

    public Map<Long, Observation> hottestIn24h(Collection<Location> locations) {
        Map<Long, Observation> mappedResults = new HashMap<>();
        locations.forEach((location) -> {
            mappedResults.put(location.getId(), hottestIn24h(location));
        });
        return mappedResults;
    }

    public Map<Long, Observation> getLatestObservations(Collection<Location> locations) {
        Map<Long, Observation> mappedResults = new HashMap<>();
        locations.forEach((location) -> {
            mappedResults.put(location.getId(), getLatestObservation(location));
        });
        return mappedResults;
    }

    private List<Observation> getlast24hObservationsByPageable(Location location, Pageable page) {

        Calendar until = Calendar.getInstance();
        until.setTimeZone(location.getTimezone());
        Calendar from = Calendar.getInstance();
        from.setTimeZone(location.getTimezone());
        from.add(Calendar.HOUR_OF_DAY, -24);
        return observationRepository.findByLocationAndObservationTimeBetween(location, from, until, page);
    }
}
