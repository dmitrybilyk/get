package com.proper.classes.service.api;

import com.proper.classes.model.Activity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ActivityService {
    Flux<Activity> getAllActivities();
    Mono<Activity> createActivity(Activity activity);
}