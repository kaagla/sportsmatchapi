package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("")
    public List<TeamView> teams() {
        return teamRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<TeamView> teamById(@PathVariable(required = true) String id) {
        return teamRepository.findById(id);
    }

    @GetMapping("sport/{sport}")
    public List<TeamView> teamsBySport(@PathVariable(required = true) String sport) {
        return teamRepository.findBySport(sport);
    }

    @GetMapping("{id}/matches")
    public List<MatchView> matchesByTeamId(@PathVariable(required = true) String id) {
        return matchRepository.findByTeamId(id);
    }
}
