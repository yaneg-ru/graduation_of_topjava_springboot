package ru.yaneg.graduation_of_topjava_springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserByPublicUserID(String publicUserId);
}
