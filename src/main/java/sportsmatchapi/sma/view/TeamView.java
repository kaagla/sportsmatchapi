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
@Table(name = "team_view")
public class TeamView extends AbstractPersistable<String> {

    private String id;
    private String name;
    private String league_id;
    private String league;
    private String sport;

}
