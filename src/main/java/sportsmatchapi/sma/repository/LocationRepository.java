package sportsmatchapi.sma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sportsmatchapi.sma.model.Location;

public interface LocationRepository extends JpaRepository<Location, String> {

    @Query("SELECT l FROM Location l WHERE l.id IN (:ids)")
    List<Location> findByIds(@Param("ids") List<String> ids);

    @Query("SELECT DISTINCT grandarea FROM Location ORDER BY grandarea ASC")
    List<String> findAllGrandareas();

    @Query("SELECT DISTINCT municipality FROM Location ORDER BY municipality ASC")
    List<String> findAllMunicipalities();

    @Query("SELECT DISTINCT postoffice FROM Location ORDER BY postoffice ASC")
    List<String> findAllPostoffices();

}