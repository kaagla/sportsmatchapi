package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sportsmatchapi.sma.view.TeamView;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamView, String> {

    @Query("SELECT t FROM TeamView t WHERE t.sport LIKE :sport")
    List<TeamView> findBySport(@Param("sport") String sport);

    @Query("SELECT t FROM TeamView t WHERE t.league_id = :id")
    List<TeamView> findByLeagueId(@Param("id") String id);

    @Query("SELECT t FROM TeamView t WHERE t.club_id = :id")
    List<TeamView> findByClubId(String id);
}