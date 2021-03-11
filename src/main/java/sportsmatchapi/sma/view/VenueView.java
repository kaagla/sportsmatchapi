package sportsmatchapi.sma.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "venue_view")
public class VenueView extends AbstractPersistable<String> {

    private String id;
    private String name;
    private String location_id;
    private String postoffice;
    private String municipality;

}
