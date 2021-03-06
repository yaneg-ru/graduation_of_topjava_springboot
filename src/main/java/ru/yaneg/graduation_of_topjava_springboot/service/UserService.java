package ru.yaneg.graduation_of_topjava_springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(String publicUserId, UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserByPublicUserID(String publicUserId);
    void deleteUser(String publicUserId);
    List<UserDto> getUsers(int page, int limit);
}
