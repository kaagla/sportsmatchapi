package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sportsmatchapi.sma.model.League;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League, String> {

    @Query("SELECT l FROM League l WHERE l.sport LIKE :sport")
    List<League> findBySport(@Param("sport") String sport);
}