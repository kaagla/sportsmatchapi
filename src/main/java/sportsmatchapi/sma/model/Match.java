package sportsmatchapi.sma.model;

import java.util.Date;
import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "matches")
public class Match extends AbstractPersistable<String> {

    private String id;
    private String date;
    private Date start_date;
    private Date end_date;
    private String time;
    private String score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="venue_id")
    private Venue venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="league_id")
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hometeam_id")
    private Team hometeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="awayteam_id")
    private Team awayteam;

}