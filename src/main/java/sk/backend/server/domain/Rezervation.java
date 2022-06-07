package sk.backend.server.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rezervation {

    @Id
    @Column(name = "id", nullable = false )
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "currentTime", nullable = false )
//    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Europe/Bratislava")
    private String currentTime;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
