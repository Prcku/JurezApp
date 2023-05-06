package sk.server.backend.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rezervation {

    @Id
    @Column(name = "id", nullable = false )
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rezervationTime", nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Europe/Bratislava")
    private Date currentTime;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
}
