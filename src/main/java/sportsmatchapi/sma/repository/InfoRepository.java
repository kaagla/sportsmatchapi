package sportsmatchapi.sma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sportsmatchapi.sma.model.Info;

import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, String> {
    @Query(value = "SELECT * FROM info LIMIT 1", nativeQuery = true)
    Optional<Info> findFirst();
}