package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sportsmatchapi.sma.model.Location;
import sportsmatchapi.sma.repository.LocationRepository;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.view.MatchView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/locations")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    MatchRepository matchRepository;

    @GetMapping("")
    public List<Location> locations(@RequestParam(required = false) List<String> ids) {
        if(ids != null) {
            return locationRepository.findByIds(ids);
        }
        return locationRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Location> location(@PathVariable(required = true) String id) {
        return locationRepository.findById(id);
    }

    @GetMapping("{id}/matches")
    public List<MatchView> matchesByLocationId(@PathVariable(required = true) String id) {
        return matchRepository.findByLocationId(id);
    }

    @GetMapping ("grandareas")
    public List<String> grandareas() {
        List<String> grandareas = locationRepository.findAllGrandareas();
        Collections.sort(grandareas);
        return grandareas;
    }

    @GetMapping ("municipalities")
    public List<String> municipalities() {
        List<String> municipalities = locationRepository.findAllMunicipalities();
        Collections.sort(municipalities);
        return municipalities;
    }

    @GetMapping ("postoffices")
    public List<String> postoffices() {
        List<String> postoffices = locationRepository.findAllPostoffices();
        Collections.sort(postoffices);
        return postoffices;
    }

}
