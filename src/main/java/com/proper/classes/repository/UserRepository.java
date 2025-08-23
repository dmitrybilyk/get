package com.proper.classes.repository;

import com.proper.classes.model.CreateUserRequest;
import com.proper.classes.model.Human;
import com.proper.classes.model.UserEntity;
import com.proper.classes.model.UserRest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {}