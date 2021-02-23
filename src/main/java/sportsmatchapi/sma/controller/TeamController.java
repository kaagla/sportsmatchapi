package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sportsmatchapi.sma.model.Location;
import sportsmatchapi.sma.repository.LocationRepository;
import sportsmatchapi.sma.view.MatchView;
import sportsmatchapi.sma.view.TeamView;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/teams")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    LocationRepository locationRepository;

    @GetMapping("")
    public List<TeamView> teams() {
        return teamRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<TeamView> teamById(@PathVariable(required = true) String id) {

        Optional<TeamView> team = teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team id " + id + " not found.");
        }

        return teamRepository.findById(id);
    }

    @GetMapping("sport/{sport}")
    public List<TeamView> teamsBySport(@PathVariable(required = true) String sport) {
        return teamRepository.findBySport(sport);
    }

    @GetMapping("{id}/matches")
    public List<MatchView> matchesByTeamId(@PathVariable(required = true) String id) {

        Optional<TeamView> team = teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team id " + id + " not found.");
        }

        return matchRepository.findByTeamId(id);
    }

    @GetMapping("{id}/locations")
    public List<Location> locationsByTeamId(@PathVariable(required = true) String id) {

        List<String> locationIds = matchRepository.findLocationIdsByTeamId(id);

        return locationRepository.findByIds(locationIds);
    }
}
