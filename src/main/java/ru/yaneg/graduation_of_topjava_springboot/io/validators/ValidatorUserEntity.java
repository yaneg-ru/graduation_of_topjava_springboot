package ru.yaneg.graduation_of_topjava_springboot.io.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.Roles;
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
        UserDetailsRequest userRequest = ((UserDetailsRequest) target);
        if (userRequest.getEmail() == null) {
            return;
        }
        UserEntity dbUser = repository.findByEmail(userRequest.getEmail().toLowerCase()).orElse(null);
        String currentPublicUserId = "";
        UserPrincipal currentUser = null;
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
             currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             currentPublicUserId = currentUser.getPublicUserId();
        }
        if (dbUser != null && !dbUser.getPublicUserId().equals(currentPublicUserId)) {
            if (currentUser!=null
                    && currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.toString()))
                    && userRequest.getEmail().equals(dbUser.getEmail())) {
                return;
            }
            errors.rejectValue("email","error", null, "constraints.user.emailMustBeUnique");
        }
    }
}
