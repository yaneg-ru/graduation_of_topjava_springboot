package ru.yaneg.graduation_of_topjava_springboot.io.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.EateryRepository;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.EateryResponse;


@Component
public class ValidatorEateryEntity implements org.springframework.validation.Validator {

    @Autowired
    private EateryRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return EateryResponse.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EateryResponse eateryResponse = ((EateryResponse) target);
        if (eateryResponse.getName() == null) {
            return;
        }
        EateryEntity dbEatery = repository.findByName(eateryResponse.getName()).orElse(null);

        if (dbEatery != null) {
            errors.rejectValue("name","error", null, "constraints.eatery.nameMustBeUnique");
        }
    }
}
