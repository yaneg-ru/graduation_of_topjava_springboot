package ru.yaneg.graduation_of_topjava_springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findUserEntityByEmail(String email);
}
