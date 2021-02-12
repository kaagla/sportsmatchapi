package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sportsmatchapi.sma.model.League;
import sportsmatchapi.sma.view.MatchView;
import sportsmatchapi.sma.view.TeamView;
import sportsmatchapi.sma.repository.LeagueRepository;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.TeamRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/leagues")
public class LeagueController {

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("")
    public List<League> leagues() {
        return leagueRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<League> leagueById(@PathVariable(required = true) String id) {

        Optional<League> league = leagueRepository.findById(id);

        if (league.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "League id " + id + " not found.");
        }

        return leagueRepository.findById(id);
    }

    @GetMapping("sport/{sport}")
    public List<League> leaguesBySport(@PathVariable(required = true) String sport) {
        return leagueRepository.findBySport(sport);
    }

    @GetMapping("{id}/teams")
    public List<TeamView> teamsByLeagueId(@PathVariable(required = true) String id) {

        Optional<League> league = leagueRepository.findById(id);

        if (league.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "League id " + id + " not found.");
        }

        return teamRepository.findByLeagueId(id);
    }

    @GetMapping("{id}/matches")
    public List<MatchView> matchesByLeagueId(@PathVariable(required = true) String id) {

        Optional<League> league = leagueRepository.findById(id);

        if (league.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "League id " + id + " not found.");
        }

        return matchRepository.findByLeagueId(id);
    }
}
