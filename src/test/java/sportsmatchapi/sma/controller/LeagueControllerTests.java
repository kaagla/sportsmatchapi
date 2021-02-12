package sportsmatchapi.sma.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sportsmatchapi.sma.model.League;
import sportsmatchapi.sma.repository.LeagueRepository;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.TeamRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LeagueControllerTests {

    @Autowired
    private LeagueController leagueController;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mockMvc;

    public LeagueControllerTests() {
    }

    @Test
    public void contextControllerLoads() throws Exception {
        assertThat(leagueController).isNotNull();
    }

    @Test
    public void contextRepositoryLoads() throws Exception {
        assertThat(leagueRepository).isNotNull();
    }

    @Test
    public void get_all_leagues() throws Exception {

        int expected_amount_of_leagues = leagueRepository.findAll().size();
        assertThat(expected_amount_of_leagues).isGreaterThan(0);

        this.mockMvc.perform(get("/api/leagues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_leagues)));
    }

    @Test
    public void get_league_by_id() throws Exception {

        String id_in_db= "l-1";

        Optional<League> league = leagueRepository.findById(id_in_db);
        assertThat(league).isNotEmpty();

        this.mockMvc.perform(get("/api/leagues/"+id_in_db))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id_in_db)));
    }

    @Test
    public void get_league_by_id_not_in_db() throws Exception {

        String id_not_in_db = "l-9999999";

        Optional<League> league = leagueRepository.findById(id_not_in_db);
        assertThat(league).isEmpty();

        this.mockMvc.perform(get("/api/leagues/"+id_not_in_db))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_leagues_by_sport() throws Exception {

        String sport = "Jalkapallo";

        int expected_amount_of_leagues = leagueRepository.findBySport(sport).size();
        assertThat(expected_amount_of_leagues).isGreaterThan(0);

        this.mockMvc.perform(get("/api/leagues/sport/"+sport))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_leagues)))
                .andExpect(jsonPath("$[0].sport", is(sport)));

    }

    @Test
    public void get_teams_by_league_id() throws Exception {

        String league_id = "l-1";

        int expected_amount_of_teams = teamRepository.findByLeagueId(league_id).size();
        assertThat(expected_amount_of_teams).isGreaterThan(0);

        this.mockMvc.perform(get("/api/leagues/"+league_id+"/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_teams)))
                .andExpect(jsonPath("$[0].league_id", is(league_id)));

    }

    @Test
    public void get_matches_by_league_id() throws Exception {

        String league_id = "l-1";
        Optional<League> league = leagueRepository.findById(league_id);
        assertThat(league).isNotEmpty();

        int expected_amount_of_matches = matchRepository.findByLeagueId(league_id).size();

        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/leagues/"+league_id+"/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)))
                .andExpect(jsonPath("$[0].league_id", is(league_id)));
    }

}
