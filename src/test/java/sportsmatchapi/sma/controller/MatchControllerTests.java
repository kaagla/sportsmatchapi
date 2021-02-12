package sportsmatchapi.sma.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.view.MatchView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MatchControllerTests {

    @Autowired
    private MatchController matchController;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextControllerLoads() throws Exception {
        assertThat(matchController).isNotNull();
    }

    @Test
    public void contextRepositoryLoads() throws Exception {
        assertThat(matchRepository).isNotNull();
    }

    @Test
    public void get_all_matches() throws Exception {

        int expected_amount_of_matches = matchRepository.findAll().size();
        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)));
    }

    @Test
    public void get_matches_by_ids() throws Exception {

        List<String> ids = new ArrayList<>();
        ids.add("m-1");
        ids.add("m-2");

        int expected_amount_of_matches = matchRepository.findByIds(ids).size();
        assertThat(expected_amount_of_matches).isEqualTo(2);

        this.mockMvc.perform(get("/api/matches?ids=m-1,m-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)));
    }

    @Test
    public void get_match_by_id() throws Exception {

        String id_in_db= "m-1";

        Optional<MatchView> match = matchRepository.findById(id_in_db);
        assertThat(match).isNotEmpty();

        this.mockMvc.perform(get("/api/matches/"+id_in_db))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id_in_db)));
    }

    @Test
    public void get_match_by_id_not_in_db() throws Exception {

        String id_not_in_db = "m-9999999";

        Optional<MatchView> match = matchRepository.findById(id_not_in_db);
        assertThat(match).isEmpty();

        this.mockMvc.perform(get("/api/matches/"+id_not_in_db))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_matches_by_dates() throws Exception {

        String dateFrom = "2021-03-01";
        String dateTo = "2021-03-02";

        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);

        int expected_amount_of_matches = matchRepository.findByDates(from,to).size();
        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/matches/dates/"+dateFrom+"/"+dateTo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)));
    }

    @Test
    public void get_matches_by_dates_and_sport() throws Exception {

        String dateFrom = "2021-03-01";
        String dateTo = "2021-03-02";

        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);

        List<String> sports = new ArrayList<>();
        sports.add("Jalkapallo");

        List<String> leagueids = new ArrayList<String>();
        leagueids.add("");
        List<String> teamids = new ArrayList<String>();
        teamids.add("");
        List<String> grandareas = new ArrayList<String>();
        grandareas.add("");
        List<String> municipalities = new ArrayList<String>();
        municipalities.add("");
        List<String> postoffices = new ArrayList<String>();
        postoffices.add("");

        int expected_amount_of_matches = matchRepository.findByDatesAndOptionalFilters(from, to, leagueids, teamids, sports, grandareas, municipalities, postoffices).size();
        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/matches/dates/"+dateFrom+"/"+dateTo+"?sports=Jalkapallo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)))
                .andExpect(jsonPath("$[0].sport", is("Jalkapallo")));
    }

}
