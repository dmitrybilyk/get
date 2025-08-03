package com.proper.classes.service.external.impl;

import com.proper.classes.service.external.api.ActivityCostService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@ConditionalOnProperty(
        name = "activity.cost",
        havingValue = "regular",
        matchIfMissing = true // optional: default to enabled
)
public class ActivityCostServiceImpl implements ActivityCostService {

    @Override
    public Mono<Double> getActivityCost(String activityName) {
        return Mono.just(100.0).delayElement(Duration.ofMillis(100));
    }
}
