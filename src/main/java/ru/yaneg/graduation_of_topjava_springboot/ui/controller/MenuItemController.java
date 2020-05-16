package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.exceptions.NotFoundEntityException;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.MenuItemEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.EateryRepository;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.MenuItemRepository;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.MenuItemRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.MenuItemResponse;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.MenuItemShortResponse;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.OperationStatusModel;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuItemController extends AbstractController {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    EateryRepository eateryRepository;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MenuItemShortResponse> getMenuItemsByDateAndEateryId(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "limit", defaultValue = "25") int limit,
                                                                     @RequestParam(value = "eateryId") Integer eateryId,
                                                                     @RequestParam(value = "date") LocalDate date) {

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));

        List<MenuItemShortResponse> returnValue = new ArrayList<>();

        if (page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<MenuItemEntity> menuItemPage = menuItemRepository.findAllByDateAndAndEatery(date, eateryEntity, pageableRequest);
        List<MenuItemEntity> menuItemEntityList = menuItemPage.getContent();

        for (MenuItemEntity menuItemEntity : menuItemEntityList) {
            MenuItemShortResponse menuItemShortResponse = modelMapper.map(menuItemEntity, MenuItemShortResponse.class);
            returnValue.add(menuItemShortResponse);
        }

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MenuItemResponse createMenuItem(@RequestParam(value = "eateryId") Integer eateryId,
                                           @Valid @RequestBody MenuItemRequest menuItemRequest) {

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));

        MenuItemEntity menuItemEntity = modelMapper.map(menuItemRequest, MenuItemEntity.class);
        menuItemEntity.setEatery(eateryEntity);

        MenuItemEntity savedMenuItem = menuItemRepository.save(menuItemEntity);

        MenuItemResponse menuItemResponse = modelMapper.map(savedMenuItem, MenuItemResponse.class);

        return menuItemResponse;

    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/{menuItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteMenuItem(@PathVariable Integer menuItemId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");

        menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundEntityException("error.menuItem.notFoundById"));

        menuItemRepository.deleteById(menuItemId);
        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(path = "/{menuItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MenuItemResponse getMenuItem(@PathVariable Integer menuItemId) {

        MenuItemEntity menuItemEntity = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundEntityException("error.menuItem.notFoundById"));

        return modelMapper.map(menuItemEntity, MenuItemResponse.class);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping(path = "/{menuItemId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MenuItemResponse updateMenuItem(@RequestParam(value = "eateryId") Integer eateryId,
                                           @PathVariable Integer menuItemId,
                                           @Valid @RequestBody MenuItemRequest menuItemRequest) {


        MenuItemEntity menuItemEntity = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundEntityException("error.menuItem.notFoundById"));


        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(() -> new NotFoundEntityException("error.eatery.notFoundById"));


        menuItemEntity.setEatery(eateryEntity);
        menuItemEntity.setName(menuItemRequest.getName());
        menuItemEntity.setDate(menuItemRequest.getDate());
        menuItemEntity.setPrice(menuItemRequest.getPrice());

        MenuItemEntity savedMenuItemEntity = menuItemRepository.save(menuItemEntity);

        return modelMapper.map(savedMenuItemEntity, MenuItemResponse.class);
    }


}
