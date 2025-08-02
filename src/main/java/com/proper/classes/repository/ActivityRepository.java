package com.proper.classes.repository;

import com.proper.classes.model.Activity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends ReactiveMongoRepository<Activity, String> {}