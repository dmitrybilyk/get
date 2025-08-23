package com.proper.classes.controller;

import com.proper.classes.model.CreateUserRequest;
import com.proper.classes.model.UserEntity;
import com.proper.classes.model.UserRest;
import com.proper.classes.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<ResponseEntity<UserRest>> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest) {
        return userService.createUser(createUserRequest)
                .map(item -> ResponseEntity.status(HttpStatus.CREATED).body(item));
    }

    @GetMapping("/{userId}")
    public Mono<UserRest> getUser(@PathVariable Integer userId) {
        return Mono.just(new UserRest());
    }


}
