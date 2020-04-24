package ru.yaneg.graduation_of_topjava_springboot.io.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.EateryRepository;
import ru.yaneg.graduation_of_topjava_springboot.shared.dto.EateryDto;


@Component
public class ValidatorEateryEntity implements org.springframework.validation.Validator {

    @Autowired
    private EateryRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return EateryDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EateryDto eateryDto = ((EateryDto) target);
        if (eateryDto.getName() == null) {
            return;
        }
        EateryEntity dbEatery = repository.findByName(eateryDto.getName());

        if (dbEatery != null) {
            errors.rejectValue("name","error", null, "eatery.fields.constrains.nameMustBeUnique");
        }
    }
}
