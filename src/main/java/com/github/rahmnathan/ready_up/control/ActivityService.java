package com.github.rahmnathan.ready_up.control;

import com.github.rahmnathan.ready_up.data.ActivityDto;
import com.github.rahmnathan.ready_up.persistence.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional
    public ActivityDto createActivity(ActivityDto activityDto){
        return activityDto;
    }
}
