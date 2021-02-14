package sportsmatchapi.sma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "locations")
public class Location extends AbstractPersistable<String> {

    private String id;
    private String address;
    private String postoffice;
    private Float lat;
    private Float lon;
    private String postalcode;
    private String municipality;
    private String grandarea;

}