package ru.yaneg.graduation_of_topjava_springboot.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.MenuItemEntity;

@Repository
public interface MenuItemRepository extends PagingAndSortingRepository<MenuItemEntity, Integer> {
    MenuItemEntity findByName(String name);
}

