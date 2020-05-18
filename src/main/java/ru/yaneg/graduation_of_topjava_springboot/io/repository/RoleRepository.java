package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    @Transactional(readOnly=true)
    RoleEntity findByName(String name);
}

