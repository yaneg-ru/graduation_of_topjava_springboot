package ru.yaneg.graduation_of_topjava_springboot.io.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.UserEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.repository.UserRepository;
import ru.yaneg.graduation_of_topjava_springboot.security.UserPrincipal;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserDetailsRequest;


@Component
public class ValidatorUserEntity implements org.springframework.validation.Validator {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDetailsRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDetailsRequest user = ((UserDetailsRequest) target);
        if (user.getEmail() == null) {
            return;
        }
        UserEntity dbUser = repository.findByEmail(user.getEmail().toLowerCase());
        String currentPublishUserId = "";
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
             UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             currentPublishUserId = userPrincipal.getPublicUserId();
        }
        if (dbUser != null && !dbUser.getPublicUserId().equals(currentPublishUserId)) {
            errors.rejectValue("email","error", null, "user.fields.constrains.emailMustBeUnique");
        }
    }
}
