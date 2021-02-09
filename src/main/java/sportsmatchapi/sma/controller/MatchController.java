package sportsmatchapi.sma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sportsmatchapi.sma.view.MatchView;
import sportsmatchapi.sma.repository.MatchRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/matches")
public class MatchController {

    @Autowired
    MatchRepository matchRepository;

    @GetMapping("")
    public List<MatchView> matches() {
        return matchRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<MatchView> matchById(@PathVariable(required = true) String id) {
        return matchRepository.findById(id);
    }

    @GetMapping("dates/{fromString}/{toString}")
    public List<MatchView> matchesByDates(@PathVariable(required = true) String fromString,
                                          @PathVariable(required = true) String toString,
                                          @RequestParam(required = false) List<String> leagueids,
                                          @RequestParam(required = false) List<String> teamids,
                                          @RequestParam(required = false) List<String> sports,
                                          @RequestParam(required = false) List<String> grandareas,
                                          @RequestParam(required = false) List<String> municipalities,
                                          @RequestParam(required = false) List<String> postoffices) {

        Date from = new Date();
        Date to = new Date();

        try {
            from = new SimpleDateFormat("yyyy-MM-dd").parse(fromString);
            to = new SimpleDateFormat("yyyy-MM-dd").parse(toString);

        } catch (java.text.ParseException e) {
        }

        if (leagueids == null) {
            leagueids = new ArrayList<String>();
            leagueids.add("");
        }

        if (teamids == null) {
            teamids = new ArrayList<String>();
            teamids.add("");
        }

        if (sports == null) {
            sports = new ArrayList<String>();
            sports.add("");
        }

        if (grandareas == null) {
            grandareas = new ArrayList<String>();
            grandareas.add("");
        }

        if (municipalities == null) {
            municipalities = new ArrayList<String>();
            municipalities.add("");
        }

        if (postoffices == null) {
            postoffices = new ArrayList<String>();
            postoffices.add("");
        }

        return matchRepository.findByDatesAndOptionalFilters(from, to, leagueids, teamids, sports, grandareas, municipalities, postoffices);

    }
}
