package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sportsmatchapi.sma.model.Club;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, String> {

    @Query("SELECT c FROM Club c WHERE c.sport LIKE :sport")
    List<Club> findBySport(@Param("sport") String sport);

}