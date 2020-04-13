package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserCreateRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.UserResponse;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    //@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse getUser(@PathVariable String id) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = userService.getUserByPublicUserID(id);
        BeanUtils.copyProperties(userDto, returnValue);

        //ModelMapper modelMapper = new ModelMapper();
        //returnValue = modelMapper.map(userDto, UserRest.class);

        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse createUser(@RequestBody UserCreateRequest userCreateRequest) {

        UserResponse returnValue = new UserResponse();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userCreateRequest, userDto);

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
