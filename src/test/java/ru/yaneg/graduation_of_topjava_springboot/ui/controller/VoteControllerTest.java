package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    @Test
    @Order(10)
    void getCountVotesByEateryIdAndDate() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/votes?date=2020-05-09&eateryId=5")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "en")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2)));
    }

    @Test
    @Order(20)
    void vote() throws Exception {

        AtomicReference<String> publicUserId = new AtomicReference<>();
        userRepository.findByEmail("yaneg.ru@gmail.com").ifPresent(userEntity -> publicUserId.set(userEntity.getPublicUserId()));

            mvc.perform(MockMvcRequestBuilders.post("/votes?eateryId=5")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept-Language", "en")
                    .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultOfVote", is("Your vote accepted. Thank You")))
                    .andExpect(jsonPath("$.publicUserId", is(publicUserId.get())))
                    .andExpect(jsonPath("$.eateryId", is(5)))
                    .andExpect(jsonPath("$.date", is(LocalDate.now().toString())));

    }

    @Test
    @Order(30)
    void getAllVotesByDate() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/votes?date=2020-05-09")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "en")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnb" +
                        "WFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2)));

    }
}