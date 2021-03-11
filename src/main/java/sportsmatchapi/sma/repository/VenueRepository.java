package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sportsmatchapi.sma.model.Venue;
import sportsmatchapi.sma.view.VenueView;

import java.util.List;

public interface VenueRepository extends JpaRepository<VenueView, String> {

    @Query("SELECT v FROM VenueView v WHERE v.location_id = :id")
    List<VenueView> findByLocationId(String id);
}