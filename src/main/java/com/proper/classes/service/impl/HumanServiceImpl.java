package com.proper.classes.service.impl;

import com.proper.classes.model.Human;
import com.proper.classes.repository.ActivityRepository;
import com.proper.classes.repository.HumanRepository;
import com.proper.classes.service.api.HumanService;
import com.proper.classes.service.external.ActivityCostClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class HumanServiceImpl implements HumanService {

    private final HumanRepository humanRepo;
    private final ActivityRepository activityRepo;
    private final ActivityCostClient costClient;

    public HumanServiceImpl(HumanRepository humanRepo, ActivityRepository activityRepo, ActivityCostClient costClient) {
        this.humanRepo = humanRepo;
        this.activityRepo = activityRepo;
        this.costClient = costClient;
    }

    @Override
    public Flux<Human> getAllHumans() {
        return humanRepo.findAll();
    }

    @Override
    public Mono<Human> createHuman(Human human) {
        return humanRepo.save(human);
    }

    @Override
    public Mono<Double> calculateTotalActivityCost(String humanId) {
        return humanRepo.findById(humanId)
                .flatMapMany(h -> Flux.fromIterable(h.activityIds()))
                .flatMap(activityRepo::findById)
                .flatMap(activity -> costClient.getActivityCost(activity.name(), humanRepo.findById(humanId).block().age()))
                .reduce(0.0, Double::sum);
    }

    @Override
    public Mono<Human> addActivityToHuman(String humanId, String activityId) {
        return humanRepo.findById(humanId)
                .switchIfEmpty(Mono.error(new RuntimeException("Human not found")))
                .flatMap(human -> {
                    List<String> updatedActivities = new ArrayList<>(human.activityIds());
                    if (!updatedActivities.contains(activityId)) {
                        updatedActivities.add(activityId);
                    }
                    Human updatedHuman = new Human(human.id(), human.name(), human.age(), updatedActivities);
                    return humanRepo.save(updatedHuman);
                });
    }
}
