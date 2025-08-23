package com.proper.classes.service;

import com.proper.classes.model.CreateUserRequest;
import com.proper.classes.model.UserEntity;
import com.proper.classes.model.UserRest;
import com.proper.classes.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserRest> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        return createUserRequestMono.map(this::convertToEntity)
                .flatMap(userRepository::save)
                .map(this::convertToRest);
    }

    private UserEntity convertToEntity(CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, userEntity);
        return userEntity;
    }

    private UserRest convertToRest(UserEntity userEntity) {
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(userEntity, userRest);
        return userRest;
    }
}
