package ru.yaneg.graduation_of_topjava_springboot.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.exceptions.NotFoundEntityException;
import ru.yaneg.graduation_of_topjava_springboot.exceptions.UniqueFieldException;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.RoleEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.RoleRepository;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.UserRepository;
import ru.yaneg.graduation_of_topjava_springboot.security.UserPrincipal;
import ru.yaneg.graduation_of_topjava_springboot.service.UserService;
import ru.yaneg.graduation_of_topjava_springboot.shared.Utils;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto user) {

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setPublicUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Set roles
        Set<RoleEntity> roleEntities = new HashSet<>();
        for (String role : user.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }

        userEntity.setRoles(roleEntities);
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEntityException("error.user.notFoundByEmail"));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEntityException("error.user.notFoundByEmail"));

        return new UserPrincipal(userEntity);

    }

    @Override
    public UserDto getUserByPublicUserID(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByPublicUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("error.user.notFoundById"));

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findByPublicUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("error.user.notFoundById"));

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        returnValue = new ModelMapper().map(updatedUserDetails, UserDto.class);

        return returnValue;
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findByPublicUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("error.user.notFoundById"));

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if (page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

}
