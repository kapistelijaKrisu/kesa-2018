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

    public List<Long> createPagingByLocationObservationsByDate(int pageNro, Location location, Calendar date) {
        List<Long> pages = new ArrayList<>();
        pages.add(0l);
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

        long smallestAbove0 = Math.max(pageNro - CONSECUTIVE_QUE_LENGTH, 1);
        long highestPageNro = Math.min(pageNro + CONSECUTIVE_QUE_LENGTH, pageCount);
        //pageNro above highest page->give page of 0 list
         if (smallestAbove0 > pageCount) {
            smallestAbove0 = 1;
            highestPageNro = Math.min(CONSECUTIVE_QUE_LENGTH, pageCount);
        }

        int a = 1;
        for (long i = smallestAbove0; i <= highestPageNro; i++) {
            pages.add(i);
        }
        if (highestPageNro != pageCount) {
            pages.add(pageCount);
        }

        return pages;
    }
}
