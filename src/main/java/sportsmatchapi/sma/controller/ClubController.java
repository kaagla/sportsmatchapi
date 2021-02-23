package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sportsmatchapi.sma.model.Club;
import sportsmatchapi.sma.model.Location;
import sportsmatchapi.sma.repository.ClubRepository;
import sportsmatchapi.sma.repository.LocationRepository;
import sportsmatchapi.sma.view.MatchView;
import sportsmatchapi.sma.view.TeamView;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.TeamRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/clubs")
public class ClubController {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LocationRepository locationRepository;

    @GetMapping("")
    public List<Club> clubs() {
        return clubRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Club> clubById(@PathVariable(required = true) String id) {

        Optional<Club> club = clubRepository.findById(id);

        if (club.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club id " + id + " not found.");
        }

        return clubRepository.findById(id);
    }

    @GetMapping("sport/{sport}")
    public List<Club> clubsBySport(@PathVariable(required = true) String sport) {
        return clubRepository.findBySport(sport);
    }

    @GetMapping("{id}/teams")
    public List<TeamView> teamsByClubId(@PathVariable(required = true) String id) {

        Optional<Club> club = clubRepository.findById(id);

        if (club.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club id " + id + " not found.");
        }

        return teamRepository.findByClubId(id);
    }

    @GetMapping("{id}/matches")
    public List<MatchView> matchesByClubId(@PathVariable(required = true) String id) {

        Optional<Club> club = clubRepository.findById(id);

        if (club.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club id " + id + " not found.");
        }

        return matchRepository.findByClubId(id);
    }

    @GetMapping("{id}/locations")
    public List<Location> locationsByClubId(@PathVariable(required = true) String id) {

        List<String> locationIds = matchRepository.findLocationIdsByClubId(id);

        return locationRepository.findByIds(locationIds);
    }
}
