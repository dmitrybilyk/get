package com.proper.classes.repository;

import com.proper.classes.model.Human;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanRepository extends ReactiveMongoRepository<Human, String> {}