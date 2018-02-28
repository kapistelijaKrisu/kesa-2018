package temperature.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import temperature.domain.Location;
import temperature.services.LocationHandler;
import temperature.services.StatisticsService;

@Controller//etusivu
public class IndexController {

    
    @Autowired
    private StatisticsService statService;
    @Autowired
    private LocationHandler locationHandler;   

    @GetMapping("/")
    public String home(Model model) {
        List<Location> locations = locationHandler.getAllLocations();
        model.addAttribute("coldest", statService.coldestIn24h(locations));
        model.addAttribute("hottest", statService.hottestIn24h(locations));
        model.addAttribute("last", statService.getLatestObservations(locations));
        model.addAttribute("locations", locations);
        
        LocalDate today = LocalDate.now();
        String datePath = today.getYear() + "-" + today.getMonthValue()+ "-" + today.getDayOfMonth();
        model.addAttribute("datePath", datePath);
        return "index";
    }
}