package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.UserDetailsRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.EateryResponse;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EateryControllerTest extends AbstractControllerTest {

    @Test
    @Order(10)
    void getEatery() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/eateries/5")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("FirstEatery")));
    }

    @Test
    @Order(20)
    void getEateryNotFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/eateries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("Eatery not found by id")));
    }

    @Test
    @Order(30)
    void getEateryNotAuthenticationUser() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/eateries/5")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "1GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(40)
    void createEatery() throws Exception {

        EateryResponse eateryResponse = new EateryResponse();
        eateryResponse.setName("TestEatery");

        mvc.perform(MockMvcRequestBuilders.post("/eateries")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eateryResponse)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("TestEatery")));
    }

    @Test
    @Order(50)
    void createEateryNameAlreadyExist() throws Exception {

        EateryResponse eateryResponse = new EateryResponse();
        eateryResponse.setName("TestEatery");

        mvc.perform(MockMvcRequestBuilders.post("/eateries")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .header("Accept-Language", "en")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eateryResponse)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("name - Object with this name already exists")));
    }

    @Test
    @Order(60)
    void deleteEateryDataIntegrityViolationException() throws Exception {

        AtomicReference<Integer> eateryId = new AtomicReference<>();
        eateryRepository.findByName("FirstEatery").ifPresent(eateryEntity -> eateryId.set(eateryEntity.getId()));

        mvc.perform(MockMvcRequestBuilders.delete("/eateries/" + eateryId.get())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("Violated the integrity of the database")));
    }

    @Test
    @Order(70)
    void updateEatery() throws Exception {

        AtomicReference<Integer> eateryId = new AtomicReference<>();
        eateryRepository.findByName("TestEatery").ifPresent(eateryEntity -> eateryId.set(eateryEntity.getId()));

        EateryResponse eateryResponse = new EateryResponse();
        eateryResponse.setName("TestEateryUpdate");

        mvc.perform(MockMvcRequestBuilders.put("/eateries/"+ eateryId.get())
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eateryResponse)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("TestEateryUpdate")));
    }

    @Test
    @Order(80)
    void updateEateryNameMustUnique() throws Exception {

        AtomicReference<Integer> eateryId = new AtomicReference<>();
        eateryRepository.findByName("TestEateryUpdate").ifPresent(eateryEntity -> eateryId.set(eateryEntity.getId()));

        EateryResponse eateryResponse = new EateryResponse();
        eateryResponse.setName("FirstEatery");

        mvc.perform(MockMvcRequestBuilders.put("/eateries/"+ eateryId.get())
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eateryResponse)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("name - Object with this name already exists")));
    }

    @Test
    @Order(90)
    void deleteEatery() throws Exception {

        AtomicReference<Integer> eateryId = new AtomicReference<>();
        eateryRepository.findByName("TestEateryUpdate").ifPresent(eateryEntity -> eateryId.set(eateryEntity.getId()));

        mvc.perform(MockMvcRequestBuilders.delete("/eateries/" + eateryId.get())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationResult", is("SUCCESS")));
    }



    @Test
    @Order(100)
    void getEateries() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/eateries")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}