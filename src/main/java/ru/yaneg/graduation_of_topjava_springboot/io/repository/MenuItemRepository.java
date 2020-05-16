package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.MenuItemEntity;

import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends PagingAndSortingRepository<MenuItemEntity, Integer> {
    Optional<MenuItemEntity> findByName(String name);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Page<MenuItemEntity> findAllByDateAndAndEatery(LocalDate date, EateryEntity eateryEntity, Pageable pageable);
}

