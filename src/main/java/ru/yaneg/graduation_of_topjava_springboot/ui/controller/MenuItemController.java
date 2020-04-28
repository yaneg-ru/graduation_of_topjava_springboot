package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.OperationStatusModel;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("eateries")
public class MenuItemController extends AbstractController {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    EateryRepository eateryRepository;


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(path = "/{eateryId}/menu", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MenuItemResponse> getAllMenuItemByEateryIdAndDate(@PathVariable Integer eateryId,
                                                                  @RequestParam(value = "date") LocalDate date) {

        List<MenuItemResponse> returnValue = new ArrayList<>();

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(()->new NotFoundEntityException("error.eatery.notFoundById"));

        List<MenuItemEntity> menuItemEntityList = menuItemRepository.findAllByDateAndAndEatery(date, eateryEntity);

        menuItemEntityList.forEach(menuItemEntity -> {
            MenuItemResponse menuItemResponse = modelMapper.map(menuItemEntity, MenuItemResponse.class);
            returnValue.add(menuItemResponse);
        });

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/{eateryId}/menu", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MenuItemResponse createMenuItem(@PathVariable Integer eateryId,
                                           @Valid @RequestBody MenuItemRequest menuItemRequest) {

        EateryEntity eateryEntity = eateryRepository.findById(eateryId)
                .orElseThrow(()-> new NotFoundEntityException("error.eatery.notFoundById"));

        MenuItemEntity menuItemEntity = modelMapper.map(menuItemRequest, MenuItemEntity.class);
        menuItemEntity.setEatery(eateryEntity);

        MenuItemEntity savedMenuItem = menuItemRepository.save(menuItemEntity);

        MenuItemResponse menuItemResponse = modelMapper.map(savedMenuItem, MenuItemResponse.class);

        return menuItemResponse;

    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/menu/{menuItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable Integer menuItemId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");

        MenuItemEntity menuItemEntity = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new NotFoundEntityException("error.menuItem.notFoundById"));

        menuItemRepository.deleteById(menuItemId);
        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(path = "/menu/{menuItemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MenuItemResponse geEatery(@PathVariable Integer menuItemId) {

        MenuItemResponse returnValue = new MenuItemResponse();

        MenuItemEntity menuItemEntity = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new NotFoundEntityException("error.menuItem.notFoundById"));

        returnValue = modelMapper.map(menuItemEntity, MenuItemResponse.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping(path = "/menu/{menuItemId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public MenuItemResponse updateMenuItem(@PathVariable Integer menuItemId,
                                           @Valid @RequestBody MenuItemRequest menuItemRequest) {

        MenuItemResponse returnValue = new MenuItemResponse();

        MenuItemEntity menuItemEntity = menuItemRepository.findById(menuItemId)
                .orElseThrow(()-> new NotFoundEntityException("error.menuItem.notFoundById"));


        EateryEntity eateryEntity = eateryRepository.findById(menuItemRequest.getEateryId())
                .orElseThrow(()->new NotFoundEntityException("error.eatery.notFoundById"));


        menuItemEntity.setEatery(eateryEntity);
        menuItemEntity.setName(menuItemRequest.getName());
        menuItemEntity.setDate(menuItemRequest.getDate());
        menuItemEntity.setPrice(menuItemRequest.getPrice());

        MenuItemEntity savedMenuItemEntity = menuItemRepository.save(menuItemEntity);

        returnValue = modelMapper.map(savedMenuItemEntity, MenuItemResponse.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(path = "/menu", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<MenuItemResponse> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "limit", defaultValue = "25") int limit,
                                           @RequestParam(value = "date") LocalDate date) {

        List<MenuItemResponse> returnValue = new ArrayList<>();

        if(page>0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<MenuItemEntity> menuItemPage = menuItemRepository.findAllByDate(date, pageableRequest);
        List<MenuItemEntity> menuItemEntityList = menuItemPage.getContent();

        for (MenuItemEntity menuItemEntity : menuItemEntityList) {
            MenuItemResponse menuItemResponse = new MenuItemResponse();
            menuItemResponse = modelMapper.map(menuItemEntity, MenuItemResponse.class);
            returnValue.add(menuItemResponse);
        }

        return returnValue;
    }
}
