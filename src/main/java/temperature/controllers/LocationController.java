package temperature.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import temperature.services.ObservationListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import temperature.domain.Location;
import temperature.domain.Observation;
import temperature.services.LocationHandler;
import temperature.services.StatisticsService;

@Controller
public class LocationController {

    @Autowired
    private LocationHandler locationHandler;
    @Autowired
    private ObservationListing observationLister;
    @Autowired
    private StatisticsService statService;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @GetMapping("/location/{id}/{date}/{page}")
    public String getByDateAndPage(Model model,
            @PathVariable long id,
            @PathVariable String date,
            @PathVariable int page) throws ParseException {

        Location location = locationHandler.getLocation(id);       
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date token = formatter.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(token);
        List<Observation> observations = observationLister.getObservationsByDate(location, page, calendar);
 
        model.addAttribute("location", location);
        model.addAttribute("coldest", statService.coldestIn24h(location));
        model.addAttribute("hottest", statService.hottestIn24h(location));
        model.addAttribute("last", statService.getLatestObservation(location));
        model.addAttribute("observations", observations);
        model.addAttribute("pages", observationLister.createPagingByLocationObservationsByDate(page, location, calendar));
        model.addAttribute("page", page);
        model.addAttribute("datePath", date);
        return "location";
    }

    @GetMapping("/location/{id}")
    public String getByDate(Model model,
            @PathVariable long id,
            @RequestParam String date) {

        return "redirect:/location/" + id + '/' + date + '/' + 0;
    }

}
