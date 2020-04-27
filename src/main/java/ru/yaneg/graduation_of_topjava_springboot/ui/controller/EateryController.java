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
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.EateryDto;
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
    public EateryDto geEatery(@PathVariable Integer id) {

        EateryDto returnValue = new EateryDto();

        EateryEntity eateryEntity = eateryRepository.findById(id).orElse(null);

        if (eateryEntity == null) {
            throw new NotFoundEntityException("error.entity.notFoundById");
        }

        returnValue = modelMapper.map(eateryEntity, EateryDto.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EateryDto createEatery(@Valid @RequestBody EateryDto eateryDto) {
        EateryDto returnValue = new EateryDto();

        EateryEntity eateryEntity = modelMapper.map(eateryDto, EateryEntity.class);
        EateryEntity createdEatery = eateryRepository.save(eateryEntity);

        returnValue = modelMapper.map(createdEatery, EateryDto.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EateryDto updateEatery(@PathVariable Integer id, @Valid @RequestBody EateryDto eateryDto) {
        EateryDto returnValue = new EateryDto();

        EateryEntity updatedEatery = eateryRepository.findById(id).orElse(null);

        if (updatedEatery==null) {
            throw new NotFoundEntityException("error.entity.notFoundById");
        }

        updatedEatery.setName(eateryDto.getName());

        eateryRepository.save(updatedEatery);

        returnValue = modelMapper.map(updatedEatery, EateryDto.class);

        return returnValue;
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable Integer id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");
        EateryEntity eateryEntity = eateryRepository.findById(id).orElse(null);
        if (eateryEntity==null) {
            throw new NotFoundEntityException("error.entity.notFoundById");
        }
        eateryRepository.deleteById(id);
        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EateryDto> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<EateryDto> returnValue = new ArrayList<>();

        if(page>0) page = page-1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<EateryEntity> eateriesPage = eateryRepository.findAll(pageableRequest);
        List<EateryEntity> eateries = eateriesPage.getContent();

        for (EateryEntity eateryEntity : eateries) {
            EateryDto eateryDto = new EateryDto();
            eateryDto = modelMapper.map(eateryEntity, EateryDto.class);
            returnValue.add(eateryDto);
        }

        return returnValue;
    }
}
