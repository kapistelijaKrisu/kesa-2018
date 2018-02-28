package temperature.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import temperature.domain.Location;
import temperature.domain.Observation;
import temperature.repository.ObservationRepository;

@Service
public class ObservationListing {

    @Autowired
    private ObservationRepository observationRepository;
    private static final int PER_PAGE = 10;
    private static final int CONSECUTIVE_QUE_LENGTH = 10;

    public List<Observation> getObservationsByDate(Location location, int pageNumber, Calendar date) {
        Pageable pageable = new PageRequest(pageNumber, PER_PAGE, Sort.Direction.DESC, "observationTime");

        Calendar until = Calendar.getInstance();
        until.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Calendar from = Calendar.getInstance();
        from.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        List<Observation> found = observationRepository.findByLocationAndObservationTimeBetween(location, from, until, pageable);
        if (found.isEmpty()) {
            return null;
        }
        return found;
    }

    public List<Integer> createPagingByLocationObservationsByDate(int pageNro, Location location, Calendar date) {
        List<Integer> pages = new ArrayList<>();
        pages.add(0);
        if (location == null) {
            return pages;
        }
        Calendar until = Calendar.getInstance();
        until.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Calendar from = Calendar.getInstance();
        from.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        long size = observationRepository.countByLocationAndObservationTimeBetween(location, from, until);
        long pageCount = size / PER_PAGE;
        if (size % PER_PAGE == 0) {
            pageCount--;
        }

        int smallestAbove0 = Math.max(pageNro - CONSECUTIVE_QUE_LENGTH, 1);
        long highestPageNro = Math.min(pageNro + CONSECUTIVE_QUE_LENGTH, pageCount);

        int a = 1;
        for (int i = smallestAbove0; i <= highestPageNro; i++) {
            pages.add(i);
        }
        if (highestPageNro != pageCount) {
            pages.add((int) pageCount);
        }

        return pages;
    }
}
