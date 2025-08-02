package com.proper.classes.service.impl;

import com.proper.classes.model.Activity;
import com.proper.classes.repository.ActivityRepository;
import com.proper.classes.service.api.ActivityService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepo;

    public ActivityServiceImpl(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public Flux<Activity> getAllActivities() {
        return activityRepo.findAll();
    }

    @Override
    public Mono<Activity> createActivity(Activity activity) {
        return activityRepo.save(activity);
    }
}
