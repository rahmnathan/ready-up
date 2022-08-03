package com.github.rahmnathan.ready_up.data;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {
    private String userId;
    private String name;
    private Set<UserDto> friends;
}
