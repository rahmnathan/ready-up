package com.github.rahmnathan.ready_up.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="activity_sequence_generator")
    @SequenceGenerator(name="activity_sequence_generator", sequenceName="ACTIVITY_SEQUENCE")
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
