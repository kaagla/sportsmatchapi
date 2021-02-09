package sportsmatchapi.sma.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "match_view")
public class MatchView extends AbstractPersistable<String> {

    private String id;
    private String date;
    private Date start_date;
    private String time;
    private String venue;
    private String location_id;
    private String sport;
    private String league_id;
    private String league;
    private String hometeam_id;
    private String hometeam;
    private String awayteam_id;
    private String awayteam;
    private String score;

    private String grandarea;
    private String municipality;
    private String postoffice;

}
