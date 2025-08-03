package com.proper.classes.controller;

import com.proper.classes.model.Activity;
import com.proper.classes.service.api.ActivityService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public Flux<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PostMapping
    public Mono<Activity> createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    @GetMapping("/{id}/activity-cost")
    public Mono<Double> getTotalActivityCost(@PathVariable String id) {
        return activityService.calculateTotalActivityCost(id);
    }
}
