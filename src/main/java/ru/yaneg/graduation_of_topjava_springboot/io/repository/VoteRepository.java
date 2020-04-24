package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.VoteEntity;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<VoteEntity, Integer> {
    VoteEntity findByName(String name);
}

