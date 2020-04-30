package ru.yaneg.graduation_of_topjava_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.*;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.*;
import ru.yaneg.graduation_of_topjava_springboot.shared.Utils;

import java.time.LocalDate;
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

    @Autowired
    EateryRepository eateryRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    VoteRepository voteRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        RoleEntity roleUser = createRole(Roles.ROLE_USER.name());
        RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name());

        if (roleAdmin == null) return;

        UserEntity adminUser = new UserEntity();;
        if (userRepository.findByEmail("yaneg.ru@gmail.com").orElse(null) == null) {
            adminUser.setFirstName("Evgeniy");
            adminUser.setLastName("Zolotarev");
            adminUser.setEmail("yaneg.ru@gmail.com");
            adminUser.setEmailVerificationStatus(true);
            adminUser.setPublicUserId(utils.generateUserId(30));
            adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
            adminUser.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));

            userRepository.save(adminUser);
        }

        UserEntity userUser = new UserEntity();;
        if (userRepository.findByEmail("user@mail.com").orElse(null) == null) {
            userUser.setFirstName("FirstName");
            userUser.setLastName("LastName");
            userUser.setEmail("user@mail.com");
            userUser.setEmailVerificationStatus(true);
            userUser.setPublicUserId(utils.generateUserId(30));
            userUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
            userUser.setRoles(new HashSet<>(Arrays.asList(roleUser)));

            userRepository.save(userUser);
        }

        LocalDate date = LocalDate.of(2020, 5, 9);

        if (eateryRepository.findById(5).orElse(null) == null) {
            EateryEntity eateryEntity = new EateryEntity();
            eateryEntity.setName("FirstEatery");

            eateryRepository.save(eateryEntity);

            MenuItemEntity menuItemEntity = new MenuItemEntity(eateryEntity, date, "Пункт меню №3", 50.0);
            menuItemRepository.save(menuItemEntity);

            menuItemEntity = new MenuItemEntity(eateryEntity, date, "Пункт меню №4", 11.77);
            menuItemRepository.save(menuItemEntity);

            VoteEntity voteEntity = new VoteEntity(eateryEntity, adminUser, date);
            voteRepository.save(voteEntity);
            voteEntity = new VoteEntity(eateryEntity, userUser, date);
            voteRepository.save(voteEntity);

        }

        if (eateryRepository.findById(10).orElse(null) == null) {
            EateryEntity eateryEntity = new EateryEntity();
            eateryEntity.setName("SecondEatery");

            eateryRepository.save(eateryEntity);


            MenuItemEntity menuItemEntity = new MenuItemEntity(eateryEntity, date, "Пункт меню №1", 90.89);
            menuItemRepository.save(menuItemEntity);

            menuItemEntity = new MenuItemEntity(eateryEntity, date, "Пункт меню №2", 55.77);
            menuItemRepository.save(menuItemEntity);

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
