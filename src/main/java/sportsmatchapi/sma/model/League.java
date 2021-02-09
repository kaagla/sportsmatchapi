package sportsmatchapi.sma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "leagues")
public class League extends AbstractPersistable<String> {

    private String id;
    private String name;
    private String sport;

}
