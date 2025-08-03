package com.proper.classes.service.external.api;

import reactor.core.publisher.Mono;

public interface ActivityCostService {
    Mono<Double> getActivityCost(String activityName);
}
