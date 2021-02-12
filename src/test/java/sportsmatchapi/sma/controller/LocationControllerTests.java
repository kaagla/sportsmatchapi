package sportsmatchapi.sma.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sportsmatchapi.sma.model.Location;
import sportsmatchapi.sma.repository.LocationRepository;
import sportsmatchapi.sma.repository.MatchRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LocationControllerTests {

    @Autowired
    private LocationController locationController;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextControllerLoads() throws Exception {
        assertThat(locationController).isNotNull();
    }

    @Test
    public void contextRepositoryLoads() throws Exception {
        assertThat(locationRepository).isNotNull();
    }

    @Test
    public void get_all_locations() throws Exception {

        int expected_amount_of_locations = locationRepository.findAll().size();
        assertThat(expected_amount_of_locations).isGreaterThan(0);

        this.mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_locations)));
    }

    @Test
    public void get_locations_by_ids() throws Exception {

        List<String> ids = new ArrayList<>();
        ids.add("loc-1");
        ids.add("loc-2");

        int expected_amount_of_locations = locationRepository.findByIds(ids).size();
        assertThat(expected_amount_of_locations).isEqualTo(2);

        this.mockMvc.perform(get("/api/locations?ids=loc-1,loc-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_locations)));
    }

    @Test
    public void get_location_by_id() throws Exception {

        String id_in_db= "loc-1";

        Optional<Location> location = locationRepository.findById(id_in_db);
        assertThat(location).isNotEmpty();

        this.mockMvc.perform(get("/api/locations/"+id_in_db))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id_in_db)));
    }

    @Test
    public void get_location_by_id_not_in_db() throws Exception {

        String id_not_in_db = "loc-9999999";

        Optional<Location> location = locationRepository.findById(id_not_in_db);

        assertThat(location).isEmpty();

        this.mockMvc.perform(get("/api/locations/"+id_not_in_db))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_matches_by_location_id() throws Exception {

        String location_id = "loc-1";
        Optional<Location> location = locationRepository.findById(location_id);
        assertThat(location).isNotEmpty();

        int expected_amount_of_matches = matchRepository.findByLocationId(location_id).size();

        assertThat(expected_amount_of_matches).isGreaterThan(0);

        this.mockMvc.perform(get("/api/locations/"+location_id+"/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(expected_amount_of_matches)))
                .andExpect(jsonPath("$[0].location_id", is(location_id)));
    }

}
