package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sportsmatchapi.sma.model.Venue;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, String> {

    @Query("SELECT v FROM Venue v WHERE v.sport LIKE :sport")
    List<Venue> findBySport(String sport);

    @Query("SELECT v FROM Venue v JOIN v.location l WHERE l.id = :id")
    List<Venue> findByLocationId(String id);
}