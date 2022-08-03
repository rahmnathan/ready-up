package com.github.rahmnathan.ready_up.web;

import com.github.rahmnathan.ready_up.control.ActivityService;
import com.github.rahmnathan.ready_up.data.ActivityDto;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(value = "/ready-up/v1/activity")
public class ActivityResource {
    private final ActivityService activityService;

    public ActivityResource(ActivityService activityService){
        this.activityService = activityService;
    }

    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ActivityDto createActivity(@RequestBody ActivityDto activity, HttpServletResponse response, Principal principal) {
        log.info("Received request: {}", activity);

        return activityService.createActivity(activity, principal.getName());
    }
}