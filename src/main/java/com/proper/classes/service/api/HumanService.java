package com.proper.classes.service.api;

import com.proper.classes.model.Human;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HumanService {
    Flux<Human> getAllHumans();
    Mono<Human> createHuman(Human human);
    Mono<Double> calculateTotalActivityCost(String humanId);
    Mono<Human> addActivityToHuman(String humanId, String activityId);
}