package com.github.rahmnathan.ready_up.persistence.control;

import com.github.rahmnathan.ready_up.persistence.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ActivityPersistenceService {
    private final ActivityRepository activityRepository;

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return authentication.getPrincipal().toString();
        }

        throw new RuntimeException();
    }
}
