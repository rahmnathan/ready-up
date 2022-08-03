package com.github.rahmnathan.ready_up.control;

import com.github.rahmnathan.ready_up.data.ActivityDto;
import com.github.rahmnathan.ready_up.data.UserDto;
import com.github.rahmnathan.ready_up.persistence.entity.Activity;
import com.github.rahmnathan.ready_up.persistence.entity.User;
import com.github.rahmnathan.ready_up.persistence.repository.ActivityRepository;
import com.github.rahmnathan.ready_up.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    @Transactional
    public ActivityDto createActivity(ActivityDto activityDto, String userId){
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow();

        Activity.ActivityBuilder activityBuilder = Activity.builder()
                .createdBy(user)
                .endDate(activityDto.getEndDate())
                .startDate(activityDto.getStartDate())
                .name(activityDto.getName())
                .createdDate(activityDto.getCreatedDate());

        if(activityDto.getMembers() != null) {
            activityBuilder.members(userRepository.findUsersByUserIdIn(activityDto.getMembers().stream().map(UserDto::getUserId).collect(Collectors.toSet())));
        }

        activityRepository.save(activityBuilder.build());

        return activityDto;
    }
}
