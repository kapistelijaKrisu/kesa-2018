package temperature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import temperature.services.LocationHandler;

@Controller
public class ObservationController {

    @Autowired
    private LocationHandler locationHandler;

    @PostMapping("/location/{id}/{date}/{page}/observation")
    public String postFromLocation(
            @PathVariable long id,
            @RequestParam String temperature,
            @PathVariable int page,
            @PathVariable String date,  
            RedirectAttributes attributes) {
        
        postTemperature(id, temperature, attributes);
        attributes.addFlashAttribute("datePath", date);
        return "redirect:/location/" + id + '/' + date + '/' + page;
    }

    @PostMapping("/location/{id}/observation")
    public String postFromIndex(@PathVariable long id, @RequestParam String temperature, RedirectAttributes attributes) {
        postTemperature(id, temperature, attributes);
        return "redirect:/";
    }
    
    private void postTemperature(long id, String temperature, RedirectAttributes attributes) {
        boolean success = locationHandler.checkAndSaveObservationToLocation(id, temperature);
        if (success) {
            attributes.addFlashAttribute("success", "Lämpötila havainto vastaanotettu!");
        } else {
            attributes.addFlashAttribute("error", "Lämpötilan havainto epäonnistui!\n Olihan lämpötila välillä -100 ja 100");
        }
    }
}