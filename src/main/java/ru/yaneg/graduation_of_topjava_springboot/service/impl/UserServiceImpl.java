package ru.yaneg.graduation_of_topjava_springboot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.UserRepository;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.Utils;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

import java.util.ArrayList;

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
            throw new RuntimeException("Record already exists");
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

    @Override
    public UserDto updateUser(String publicUserId, UserDto user) {
        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findByPublicUserId(publicUserId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User with ID: " + publicUserId + " not found");
        }

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByPublicUserID(String publicUserId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByPublicUserId(publicUserId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User with ID: " + publicUserId + " not found");
        }
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String publicUserId) {
        UserEntity userEntity = userRepository.findByPublicUserId(publicUserId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User with ID: " + publicUserId + " not found");
        }
        userRepository.delete(userEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
