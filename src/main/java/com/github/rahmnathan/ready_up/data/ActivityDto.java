package com.github.rahmnathan.ready_up.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {
    private String name;
    private UserDto createdBy;
    private Set<UserDto> members;
    private LocalDateTime createdDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
