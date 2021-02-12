package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sportsmatchapi.sma.model.Match;
import sportsmatchapi.sma.view.MatchView;

import java.util.Date;
import java.util.List;

public interface MatchRepository extends JpaRepository<MatchView, String> {

    @Query("SELECT m FROM MatchView m WHERE m.id in (:ids)")
    List<MatchView> findByIds(@Param("ids") List<String> ids);

    @Query("SELECT m FROM MatchView m WHERE m.start_date BETWEEN :from AND :to")
    List<MatchView> findByDates(@Param("from") Date from, @Param("to") Date to);

    @Query("SELECT m FROM MatchView m WHERE m.start_date BETWEEN :from AND :to AND (" +
            "((:leagueids) IS NULL OR m.league_id IN (:leagueids)) OR " +
            "((:teamids) IS NULL OR m.hometeam_id IN (:teamids)) OR " +
            "((:teamids) IS NULL OR m.awayteam_id IN (:teamids)) OR " +
            "((:sports) IS NULL OR m.sport IN (:sports)) OR " +
            "((:grandareas) IS NULL OR m.grandarea IN (:grandareas)) OR " +
            "((:municipalities) IS NULL OR m.municipality IN (:municipalities)) OR " +
            "((:postoffices) IS NULL OR m.postoffice IN (:postoffices)))")
    List<MatchView> findByDatesAndOptionalFilters(
            @Param("from") Date from,
            @Param("to") Date to,
            @Param("leagueids") List<String> leagueids,
            @Param("teamids") List<String> teamids,
            @Param("sports") List<String> sports,
            @Param("grandareas") List<String> grandareas,
            @Param("municipalities") List<String> municipalities,
            @Param("postoffices") List<String> postoffices);

    @Query("SELECT m FROM MatchView m WHERE m.hometeam_id = :id OR m.awayteam_id = :id")
    List<MatchView> findByTeamId(@Param("id") String id);

    @Query("SELECT m FROM MatchView m WHERE m.league_id = :id")
    List<MatchView> findByLeagueId(@Param("id") String id);

    @Query("SELECT m FROM MatchView m WHERE m.location_id = :id")
    List<MatchView> findByLocationId(@Param("id") String id);
}