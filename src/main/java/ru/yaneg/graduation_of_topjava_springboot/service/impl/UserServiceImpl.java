package ru.yaneg.graduation_of_topjava_springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yaneg.graduation_of_topjava_springboot.io.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.repository.UserRepository;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) {

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId("test userId");

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
}
