package sportsmatchapi.sma.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.TeamRepository;
import sportsmatchapi.sma.view.TeamView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TeamControllerTests {

    @Autowired
    private TeamController teamController;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextControllerLoads() throws Exception {
        assertThat(teamController).isNotNull();
    }

    @Test
    public void contextRepositoryLoads() throws Exception {
        assertThat(teamRepository).isNotNull();
    }

    @Test
    public void get_all_teams() throws Exception {

        int expected_amount_of_teams = teamRepository.findAll().size();
        assertThat(expected_amount_of_teams).isGreaterThan(0);

        this.mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_teams)));
    }

    @Test
    public void get_team_by_id() throws Exception {

        String id_in_db= "t-1";

        Optional<TeamView> team = teamRepository.findById(id_in_db);
        assertThat(team).isNotEmpty();

        this.mockMvc.perform(get("/api/teams/"+id_in_db))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id_in_db)));
    }

    @Test
    public void get_team_by_id_not_in_db() throws Exception {

        String id_not_in_db = "t-9999999";

        Optional<TeamView> team = teamRepository.findById(id_not_in_db);
        assertThat(team).isEmpty();

        this.mockMvc.perform(get("/api/teams/"+id_not_in_db))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_teams_by_sport() throws Exception {

        String sport = "Koripallo";

        int expected_amount_of_teams = teamRepository.findBySport(sport).size();
        assertThat(expected_amount_of_teams).isGreaterThan(0);

        this.mockMvc.perform(get("/api/teams/sport/"+sport))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_teams)))
                .andExpect(jsonPath("$[0].sport", is(sport)));

    }

    @Test
    public void get_matches_by_team_id() throws Exception {

        String team_id = "t-1";
        Optional<TeamView> team = teamRepository.findById(team_id);
        assertThat(team).isNotEmpty();

        int expected_amount_of_matches = matchRepository.findByTeamId(team_id).size();

        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/teams/"+team_id+"/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)));
    }

}
