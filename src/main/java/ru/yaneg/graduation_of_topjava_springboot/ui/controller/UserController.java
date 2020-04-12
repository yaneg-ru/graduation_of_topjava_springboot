package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.UserResponse;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser() {
        return "get user was called";
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {

        UserResponse returnValue = new UserResponse();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userDto);

        //ModelMapper modelMapper = new ModelMapper();
        //UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        //returnValue = modelMapper.map(createdUser, UserRest.class);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
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
