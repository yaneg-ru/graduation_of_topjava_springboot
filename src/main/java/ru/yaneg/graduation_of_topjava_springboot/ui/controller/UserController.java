package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserDetailsRequestModel;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @GetMapping
    public String getUser() {
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        return null;
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }
}
