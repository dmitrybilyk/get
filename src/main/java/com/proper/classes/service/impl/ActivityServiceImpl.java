package com.proper.classes.service.impl;

import com.proper.classes.model.Activity;
import com.proper.classes.repository.ActivityRepository;
import com.proper.classes.service.api.ActivityService;
import com.proper.classes.service.external.api.ActivityCostService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepo;
    private final ActivityCostService activityCostService;

    public ActivityServiceImpl(ActivityRepository activityRepo, ActivityCostService activityCostService) {
        this.activityRepo = activityRepo;
        this.activityCostService = activityCostService;
    }

    @Override
    public Flux<Activity> getAllActivities() {
        return activityRepo.findAll();
    }

    @Override
    public Mono<Activity> createActivity(Activity activity) {
        return activityRepo.save(activity);
    }

    @Override
    public Mono<Double> calculateTotalActivityCost(String activityName) {
        return activityCostService.getActivityCost(activityName);
    }
}
