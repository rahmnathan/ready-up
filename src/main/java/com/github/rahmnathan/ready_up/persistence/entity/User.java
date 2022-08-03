package com.github.rahmnathan.ready_up.persistence.entity;

import com.github.rahmnathan.ready_up.data.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = @Index(columnList = "userId"))
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="user_sequence_generator")
    @SequenceGenerator(name="user_sequence_generator", sequenceName="USER_SEQUENCE")
    private Long id;

    private String name;
    private String userId;

    @ManyToMany(mappedBy = "userId")
    private Set<User> friends;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId);
    }
}
