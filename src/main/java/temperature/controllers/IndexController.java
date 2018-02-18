package temperature.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import temperature.domain.ObservationSpot;
import temperature.repository.ObservationSpotRepository;

@Transactional
@Controller//etusivu
public class IndexController {

    @Autowired
    private ObservationSpotRepository observationRepositoryRepo;

    @GetMapping("/")
    public String list(Model model) {
        
        List<ObservationSpot> observationSpots = observationRepositoryRepo.findAll();
        observationSpots.add(new ObservationSpot("test"));
        model.addAttribute("observationSpots", observationSpots);
        return "index";
    }
}