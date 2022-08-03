package com.github.rahmnathan.ready_up.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="activity_sequence_generator")
    @SequenceGenerator(name="activity_sequence_generator", sequenceName="ACTIVITY_SEQUENCE")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "userId")
    private Set<User> members;

    @ManyToOne
    private User createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(name, activity.name) && Objects.equals(members, activity.members) && Objects.equals(createdBy, activity.createdBy) && Objects.equals(createdDate, activity.createdDate) && Objects.equals(startDate, activity.startDate) && Objects.equals(endDate, activity.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members, createdBy, createdDate, startDate, endDate);
    }
}
