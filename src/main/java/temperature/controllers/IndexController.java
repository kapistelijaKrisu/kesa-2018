package temperature.controllers;

import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import temperature.domain.Location;
import temperature.repository.ObservationSpotRepository;

@Transactional
@Controller//etusivu
public class IndexController {

    @Autowired
    private ObservationSpotRepository observationRepositoryRepo;

    @GetMapping("/")
    public String list(Model model) {
        
        List<Location> observationSpots = observationRepositoryRepo.findAll();
        observationSpots.add(new Location("test", 12.22, 21.22, TimeZone.getDefault(), null));
        model.addAttribute("observationSpots", observationSpots);
        return "index";
    }
}