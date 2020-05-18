package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer> {

    @Transactional(readOnly=true)
    Optional<UserEntity> findByEmail(String email);

    @Transactional(readOnly=true)
    Optional<UserEntity> findByPublicUserId(String publicUserId);
}
