package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.MenuItemEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuItemRepository extends PagingAndSortingRepository<MenuItemEntity, Integer> {
    MenuItemEntity findByName(String name);
    List<MenuItemEntity> findAllByDateAndAndEatery(LocalDate date, EateryEntity eateryEntity);
    Page<MenuItemEntity> findAllByDate(LocalDate date, Pageable pageable);
}

