package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

import java.util.Optional;

@Repository
public interface EateryRepository extends PagingAndSortingRepository<EateryEntity, Integer> {
    Optional<EateryEntity> findByName(String name);
}

