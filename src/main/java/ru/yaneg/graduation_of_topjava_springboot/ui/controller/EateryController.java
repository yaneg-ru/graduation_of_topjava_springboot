package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.yaneg.graduation_of_topjava_springboot.exceptions.NotFoundEntityException;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.EateryRepository;
import ru.yaneg.graduation_of_topjava_springboot.io.validators.ValidatorEateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.EateryResponse;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.OperationStatusModel;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("eateries")
public class EateryController extends AbstractController {

    @Autowired
    EateryRepository eateryRepository;

    @Autowired
    private ValidatorEateryEntity validatorEateryEntity;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validatorEateryEntity);
    }


    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public EateryResponse getEatery(@PathVariable Integer id) {

        EateryResponse returnValue = new EateryResponse();

        EateryEntity eateryEntity = eateryRepository.findById(id)
                .orElseThrow(()-> new NotFoundEntityException("error.eatery.notFoundById"));

        returnValue = modelMapper.map(eateryEntity, EateryResponse.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EateryResponse createEatery(@Valid @RequestBody EateryResponse eateryResponse) {
        EateryResponse returnValue = new EateryResponse();

        EateryEntity eateryEntity = modelMapper.map(eateryResponse, EateryEntity.class);
        EateryEntity createdEatery = eateryRepository.save(eateryEntity);

        returnValue = modelMapper.map(createdEatery, EateryResponse.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EateryResponse updateEatery(@PathVariable Integer id, @Valid @RequestBody EateryResponse eateryResponse) {
        EateryResponse returnValue = new EateryResponse();

        EateryEntity updatedEatery = eateryRepository.findById(id)
                .orElseThrow(()-> new NotFoundEntityException("error.eatery.notFoundById"));

        updatedEatery.setName(eateryResponse.getName());

        eateryRepository.save(updatedEatery);

        returnValue = modelMapper.map(updatedEatery, EateryResponse.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable Integer id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");

        EateryEntity eateryEntity = eateryRepository.findById(id)
                .orElseThrow(()-> new NotFoundEntityException("error.eatery.notFoundById"));

        eateryRepository.deleteById(id);
        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EateryResponse> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<EateryResponse> returnValue = new ArrayList<>();

        if(page>0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<EateryEntity> eateriesPage = eateryRepository.findAll(pageableRequest);
        List<EateryEntity> eateries = eateriesPage.getContent();

        for (EateryEntity eateryEntity : eateries) {
            EateryResponse eateryResponse = new EateryResponse();
            eateryResponse = modelMapper.map(eateryEntity, EateryResponse.class);
            returnValue.add(eateryResponse);
        }

        return returnValue;
    }
}
