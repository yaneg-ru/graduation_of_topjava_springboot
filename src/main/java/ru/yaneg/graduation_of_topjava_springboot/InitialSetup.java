package ru.yaneg.graduation_of_topjava_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.RoleEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.Roles;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.RoleRepository;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.UserRepository;
import ru.yaneg.graduation_of_topjava_springboot.shared.Utils;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class InitialSetup {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utils utils;

    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        createRole(Roles.ROLE_USER.name());
        RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name());

        if(roleAdmin == null) return;

        if (userRepository.findByEmail("yaneg.ru@gmail.com")==null) {
            UserEntity adminUser = new UserEntity();
            adminUser.setFirstName("Evgeniy");
            adminUser.setLastName("Zolotarev");
            adminUser.setEmail("yaneg.ru@gmail.com");
            adminUser.setEmailVerificationStatus(true);
            adminUser.setPublicUserId(utils.generateUserId(30));
            adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
            adminUser.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));

            userRepository.save(adminUser);
        }

    }

    @Transactional
    protected RoleEntity createRole(String name) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            roleRepository.save(role);
        }
        return role;
    }

}
