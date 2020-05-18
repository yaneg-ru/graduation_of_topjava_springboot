package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

import java.util.Optional;

@Repository
public interface EateryRepository extends PagingAndSortingRepository<EateryEntity, Integer> {
    @Transactional(readOnly=true)
    Optional<EateryEntity> findByName(String name);
}

