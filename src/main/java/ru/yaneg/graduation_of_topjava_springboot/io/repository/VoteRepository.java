package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.VoteEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<VoteEntity, Integer> {

    @Transactional(readOnly=true)
    Integer countAllByDateAndEatery(LocalDate date, EateryEntity eateryEntity);

    @Transactional(readOnly=true)
    Integer countAllByDate(LocalDate date);

    @Transactional(readOnly=true)
    VoteEntity findByDateAndUser(LocalDate date, UserEntity userEntity);
}

