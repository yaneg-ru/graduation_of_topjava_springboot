package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.io.validators.ValidatorUserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.Roles;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserDetailsRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.OperationStatusModel;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.UserResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController  extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    private ValidatorUserEntity emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }


    private ModelMapper modelMapper = new ModelMapper();

    @PostAuthorize("hasRole('ADMIN') or returnObject.publicUserId == principal.publicUserId")
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse getUser(@PathVariable String id) {
        logger.info("getUser by publicUserID = {}", id);
        UserResponse returnValue = new UserResponse();

        UserDto userDto = userService.getUserByPublicUserID(id);

        returnValue = modelMapper.map(userDto, UserResponse.class);

        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse createUser(@Valid @RequestBody UserDetailsRequest userDetailsRequest) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = modelMapper.map(userDetailsRequest, UserDto.class);
        userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserResponse.class);

        return returnValue;
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.publicUserId")
    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public UserResponse updateUser(@PathVariable String id, @Valid @RequestBody UserDetailsRequest userDetailsRequest) {
        UserResponse returnValue = new UserResponse();

        UserDto userDto = modelMapper.map(userDetailsRequest, UserDto.class);

        UserDto updatedUser = userService.updateUser(id, userDto);
        returnValue = modelMapper.map(updatedUser, UserResponse.class);

        return returnValue;
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.publicUserId")
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");
        userService.deleteUser(id);
        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @Secured({ "ROLE_ADMIN" })
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
