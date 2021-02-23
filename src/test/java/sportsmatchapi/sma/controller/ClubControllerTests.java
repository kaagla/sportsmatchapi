package sportsmatchapi.sma.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sportsmatchapi.sma.model.Club;
import sportsmatchapi.sma.repository.ClubRepository;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.TeamRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClubControllerTests {

    @Autowired
    private ClubController clubController;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextControllerLoads() throws Exception {
        assertThat(clubController).isNotNull();
    }

    @Test
    public void contextRepositoryLoads() throws Exception {
        assertThat(clubRepository).isNotNull();
    }

    @Test
    public void get_all_clubs() throws Exception {

        int expected_amount_of_clubs = clubRepository.findAll().size();
        assertThat(expected_amount_of_clubs).isGreaterThan(0);

        this.mockMvc.perform(get("/api/clubs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_clubs)));
    }

    @Test
    public void get_club_by_id() throws Exception {

        String id_in_db= "c-1";

        Optional<Club> club = clubRepository.findById(id_in_db);
        assertThat(club).isNotEmpty();

        this.mockMvc.perform(get("/api/clubs/"+id_in_db))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id_in_db)));
    }

    @Test
    public void get_club_by_id_not_in_db() throws Exception {

        String id_not_in_db = "c-9999999";

        Optional<Club> club = clubRepository.findById(id_not_in_db);
        assertThat(club).isEmpty();

        this.mockMvc.perform(get("/api/clubs/"+id_not_in_db))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_clubs_by_sport() throws Exception {

        String sport = "Jalkapallo";

        int expected_amount_of_clubs = clubRepository.findBySport(sport).size();
        assertThat(expected_amount_of_clubs).isGreaterThan(0);

        this.mockMvc.perform(get("/api/clubs/sport/"+sport))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_clubs)))
                .andExpect(jsonPath("$[0].sport", is(sport)));

    }

    @Test
    public void get_teams_by_club_id() throws Exception {

        String club_id = "c-1";

        int expected_amount_of_teams = teamRepository.findByClubId(club_id).size();
        assertThat(expected_amount_of_teams).isGreaterThan(0);

        this.mockMvc.perform(get("/api/clubs/"+club_id+"/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_teams)))
                .andExpect(jsonPath("$[0].club_id", is(club_id)));

    }

    @Test
    public void get_matches_by_club_id() throws Exception {

        String club_id = "c-1";
        Optional<Club> club = clubRepository.findById(club_id);
        assertThat(club).isNotEmpty();

        int expected_amount_of_matches = matchRepository.findByClubId(club_id).size();
        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/clubs/"+club_id+"/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)));
    }

    @Test
    public void get_locations_by_club_id() throws Exception {
        String club_id = "c-1";
        Optional<Club> club = clubRepository.findById(club_id);
        assertThat(club).isNotEmpty();

        List<String> locationIds = matchRepository.findLocationIdsByClubId(club_id);

        int expected_amount_of_locations = locationIds.size();
        assertThat(expected_amount_of_locations).isGreaterThan(0);

        this.mockMvc.perform(get("/api/clubs/"+club_id+"/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_locations)))
                .andExpect(jsonPath("$[0].id", in(locationIds)));
    }

}
