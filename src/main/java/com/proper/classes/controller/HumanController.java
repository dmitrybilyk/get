package com.proper.classes.controller;

import com.proper.classes.model.Human;
import com.proper.classes.service.api.HumanService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/humans")
public class HumanController {

    private final HumanService humanService;

    public HumanController(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping
    public Flux<Human> getAllHumans() {
        return humanService.getAllHumans();
    }

    @PostMapping
    public Mono<Human> createHuman(@RequestBody Human human) {
        return humanService.createHuman(human);
    }

    @GetMapping("/{id}/activity-cost")
    public Mono<Double> getTotalActivityCost(@PathVariable String id) {
        return humanService.calculateTotalActivityCost(id);
    }

    @PatchMapping("/{humanId}/activities/{activityId}")
    public Mono<Human> addActivityToHuman(@PathVariable String humanId, @PathVariable String activityId) {
        return humanService.addActivityToHuman(humanId, activityId);
    }
}
