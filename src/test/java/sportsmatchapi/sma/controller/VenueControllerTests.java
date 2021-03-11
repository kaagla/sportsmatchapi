package sportsmatchapi.sma.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sportsmatchapi.sma.model.Venue;
import sportsmatchapi.sma.repository.MatchRepository;
import sportsmatchapi.sma.repository.VenueRepository;
import sportsmatchapi.sma.view.VenueView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class VenueControllerTests {

    @Autowired
    private VenueController venueController;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextControllerLoads() throws Exception {
        assertThat(venueController).isNotNull();
    }

    @Test
    public void contextRepositoryLoads() throws Exception {
        assertThat(venueRepository).isNotNull();
    }

    @Test
    public void get_all_venues() throws Exception {

        int expected_amount_of_venues = venueRepository.findAll().size();
        assertThat(expected_amount_of_venues).isGreaterThan(0);

        this.mockMvc.perform(get("/api/venues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_venues)));
    }

    @Test
    public void get_venue_by_id() throws Exception {

        String id_in_db= "v-1";

        Optional<VenueView> venue = venueRepository.findById(id_in_db);
        assertThat(venue).isNotEmpty();

        this.mockMvc.perform(get("/api/venues/"+id_in_db))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id_in_db)));
    }

    @Test
    public void get_venue_by_id_not_in_db() throws Exception {

        String id_not_in_db = "v-9999999";

        Optional<VenueView> venue = venueRepository.findById(id_not_in_db);
        assertThat(venue).isEmpty();

        this.mockMvc.perform(get("/api/venues/"+id_not_in_db))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_matches_by_venue_id() throws Exception {

        String venue_id = "v-1";
        Optional<VenueView> venue = venueRepository.findById(venue_id);
        assertThat(venue).isNotEmpty();

        int expected_amount_of_matches = matchRepository.findByVenueId(venue_id).size();
        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/venues/"+venue_id+"/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)));
    }

}
