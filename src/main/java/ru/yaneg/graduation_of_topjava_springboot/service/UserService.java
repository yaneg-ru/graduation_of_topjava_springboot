package ru.yaneg.graduation_of_topjava_springboot.service;

import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
