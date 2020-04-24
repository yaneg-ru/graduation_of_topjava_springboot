package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

@Repository
public interface MenuRepository extends PagingAndSortingRepository<EateryEntity, Integer> {
    EateryEntity findByName(String name);
}

