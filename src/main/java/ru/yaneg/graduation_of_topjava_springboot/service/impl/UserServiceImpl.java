package ru.yaneg.graduation_of_topjava_springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yaneg.graduation_of_topjava_springboot.io.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.repository.UserRepository;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.Utils;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw  new RuntimeException("Record already exists");
        }

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPublicUserId(utils.generateAlphabetUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }


}
