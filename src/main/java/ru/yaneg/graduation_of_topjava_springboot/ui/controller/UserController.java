package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserDetailsRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.OperationStatusModel;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.RequestOperationStatus;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public UserResponse getUser(@PathVariable String id) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = userService.getUserByPublicUserID(id);

        returnValue = modelMapper.map(userDto, UserResponse.class);

        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse createUser(@RequestBody UserDetailsRequest userDetailsRequest) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = modelMapper.map(userDetailsRequest, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserResponse.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequest userDetailsRequest) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = modelMapper.map(userDetailsRequest, UserDto.class);

        UserDto updatedUser = userService.updateUser(id, userDto);
        returnValue = modelMapper.map(updatedUser, UserResponse.class);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserResponse> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserResponse userModel = new UserResponse();
            userModel = modelMapper.map(userDto, UserResponse.class);
			returnValue.add(userModel);
		}

        return returnValue;
    }
}
