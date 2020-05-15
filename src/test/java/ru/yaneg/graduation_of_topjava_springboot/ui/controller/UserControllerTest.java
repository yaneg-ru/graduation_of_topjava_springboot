package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserDetailsRequest;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest{

    @Test
    @Order(10)
    void getUser() throws Exception {

        AtomicReference<String> publicUserId = new AtomicReference<>();
        userRepository.findByEmail("yaneg.ru@gmail.com").ifPresent(userEntity -> publicUserId.set(userEntity.getPublicUserId()));

        mvc.perform(MockMvcRequestBuilders.get("/users/" + publicUserId.get())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicUserId", is(publicUserId.get())))
                .andExpect(jsonPath("$.firstName", is("Evgeniy")))
                .andExpect(jsonPath("$.lastName", is("Zolotarev")))
                .andExpect(jsonPath("$.email", is("yaneg.ru@gmail.com")));
    }

    @Test
    @Order(20)
    void getUserNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1dOrsrg6UjrBZYWS1dHUyLEJ7PWusPP")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "en")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("User not found by publicId")));
    }

    @Test
    @Order(30)
    void getUserNotAuthenticationUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/dOrsrg6UjrBZYWS1dHUyLEJ7PWusPP")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(40)
    void createUser() throws Exception {

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setEmail("test@mail.com");
        userDetailsRequest.setFirstName("Ivan");
        userDetailsRequest.setLastName("Ivanov");
        userDetailsRequest.setPassword("1234567");

        mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDetailsRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Ivan")))
                .andExpect(jsonPath("$.lastName", is("Ivanov")))
                .andExpect(jsonPath("$.email", is("test@mail.com")));
    }

    @Test
    @Order(50)
    void createUserWithNoUniqueEmail() throws Exception {

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setEmail("yaneg.ru@gmail.com");
        userDetailsRequest.setFirstName("Ivan");
        userDetailsRequest.setLastName("Ivanov");
        userDetailsRequest.setPassword("1234567");

        mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "en")
                .content(mapper.writeValueAsString(userDetailsRequest)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("email - User with this email already exists")));
    }

    @Test
    @Order(60)
    void deleteUser() throws Exception {

        AtomicReference<String> publicUserId = new AtomicReference<>();
        userRepository.findByEmail("test@mail.com").ifPresent(userEntity -> publicUserId.set(userEntity.getPublicUserId()));

        mvc.perform(MockMvcRequestBuilders.delete("/users/" + publicUserId.get())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationResult", is("SUCCESS")));
    }

    @Test
    @Order(70)
    void updateUserDetailsByAdmin() throws Exception {

        AtomicReference<String> publicUserId = new AtomicReference<>();
        userRepository.findByEmail("user@mail.com").ifPresent(userEntity -> publicUserId.set(userEntity.getPublicUserId()));

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setEmail("user@mail.com");
        userDetailsRequest.setFirstName("Ivan_");
        userDetailsRequest.setLastName("Ivanov_");
        userDetailsRequest.setPassword("1234567");

        mvc.perform(MockMvcRequestBuilders.put("/users/" + publicUserId.get())
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDetailsRequest)))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicUserId", is(publicUserId.get())))
                .andExpect(jsonPath("$.firstName", is("Ivan_")))
                .andExpect(jsonPath("$.lastName", is("Ivanov_")))
                .andExpect(jsonPath("$.email", is("user@mail.com")));
    }

    @Test
    @Order(80)
    void updateUserDetailsByUser() throws Exception {

        AtomicReference<String> publicUserId = new AtomicReference<>();
        userRepository.findByEmail("user@mail.com").ifPresent(userEntity -> publicUserId.set(userEntity.getPublicUserId()));

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setEmail("user@mail.com");
        userDetailsRequest.setFirstName("Ivan");
        userDetailsRequest.setLastName("Ivanov");
        userDetailsRequest.setPassword("1234567");

        mvc.perform(MockMvcRequestBuilders.put("/users/" + publicUserId.get())
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQG1haWwuY29tIn0.DZkw3_YDkd6RbORZ1fi_vKHp1U4YmZKDWAMgwwl1TxHKI1cYmQsWvnVlxvXjBgFhuC7euHxhtA6MGRiCrw_o2A")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDetailsRequest)))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicUserId", is(publicUserId.get())))
                .andExpect(jsonPath("$.firstName", is("Ivan")))
                .andExpect(jsonPath("$.lastName", is("Ivanov")))
                .andExpect(jsonPath("$.email", is("user@mail.com")));
    }


    @Test
    @Order(90)
    void updateUserDetailsByUserWithNoUniqueEmail() throws Exception {

        AtomicReference<String> publicUserId = new AtomicReference<>();
        userRepository.findByEmail("user@mail.com").ifPresent(userEntity -> publicUserId.set(userEntity.getPublicUserId()));

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setEmail("yaneg.ru@gmail.com");
        userDetailsRequest.setFirstName("Ivan");
        userDetailsRequest.setLastName("Ivanov");
        userDetailsRequest.setPassword("1234567");

        mvc.perform(MockMvcRequestBuilders.put("/users/" + publicUserId.get())
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQG1haWwuY29tIn0.DZkw3_YDkd6RbORZ1fi_vKHp1U4YmZKDWAMgwwl1TxHKI1cYmQsWvnVlxvXjBgFhuC7euHxhtA6MGRiCrw_o2A")
                .header("Accept-Language", "en")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDetailsRequest)))

                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("email - User with this email already exists")));
    }


    @Test
    @Order(-10)
    void getUsers() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}