package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sportsmatchapi.sma.repository.VenueRepository;
import sportsmatchapi.sma.view.MatchView;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.view.VenueView;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/venues")
public class VenueController {

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    MatchRepository matchRepository;

    @GetMapping("")
    public List<VenueView> venues() {
        return venueRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<VenueView> venueById(@PathVariable(required = true) String id) {

        Optional<VenueView> venue = venueRepository.findById(id);

        if (venue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue id " + id + " not found.");
        }

        return venue;
    }

    @GetMapping("{id}/matches")
    public List<MatchView> matchesByVenueId(@PathVariable(required = true) String id) {

        Optional<VenueView> venue = venueRepository.findById(id);

        if (venue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue id " + id + " not found.");
        }

        return matchRepository.findByVenueId(id);
    }
}
