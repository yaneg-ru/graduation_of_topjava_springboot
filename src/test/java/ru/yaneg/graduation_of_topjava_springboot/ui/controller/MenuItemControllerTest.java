package ru.yaneg.graduation_of_topjava_springboot.ui.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.request.MenuItemRequest;
import ru.yaneg.graduation_of_topjava_springboot.ui.model.response.EateryResponse;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuItemControllerTest extends AbstractControllerTest {

    @Test
    @Order(10)
    void getMenuItem() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/menu/6")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Пункт меню №3")))
                .andExpect(jsonPath("$.date", is("2020-05-09")))
                .andExpect(jsonPath("$.price", is(50.0)))
                .andExpect(jsonPath("$.eatery.id", is(5)));
    }

    @Test
    @Order(20)
    void getMenuItemsByEateryIdAndDate() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/menu?date=2020-05-09&eateryId=10")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(40)
    void createMenuItem() throws Exception {

        MenuItemRequest menuItemRequest = new MenuItemRequest();
        menuItemRequest.setName("TestMenuItem");
        menuItemRequest.setDate(LocalDate.of(2020, 05, 10));
        menuItemRequest.setPrice(10.5);

        mvc.perform(MockMvcRequestBuilders.post("/menu?eateryId=5")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("TestMenuItem")))
                .andExpect(jsonPath("$.date", is("2020-05-10")))
                .andExpect(jsonPath("$.price", is(10.5)))
                .andExpect(jsonPath("$.eatery.id", is(5)));

    }

    @Test
    @Order(50)
    void createMenuItemWithDataIntegrityViolation() throws Exception {

        LocalDate localDate = LocalDate.of(2020, 05, 10);
        MenuItemRequest menuItemRequest = new MenuItemRequest();
        menuItemRequest.setName("TestMenuItem");
        menuItemRequest.setDate(localDate);
        menuItemRequest.setPrice(10.5);

        mvc.perform(MockMvcRequestBuilders.post("/menu?eateryId=5")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .header("Accept-Language", "en")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemRequest)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("Violated the integrity of the database")));
    }

    @Test
    @Order(60)
    void updateMenuItem() throws Exception {

        AtomicReference<Integer> menuItemId = new AtomicReference<>();
        menuItemRepository.findByName("TestMenuItem").ifPresent(menuItemEntity -> menuItemId.set(menuItemEntity.getId()));

        MenuItemRequest menuItemRequest = new MenuItemRequest();
        menuItemRequest.setName("TestMenuItemUpdate");
        menuItemRequest.setDate(LocalDate.of(2020, 5, 10));
        menuItemRequest.setPrice(10.5);

        mvc.perform(MockMvcRequestBuilders.put("/menu/" + menuItemId.get() + "?eateryId=5")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("TestMenuItemUpdate")))
                .andExpect(jsonPath("$.date", is("2020-05-10")))
                .andExpect(jsonPath("$.price", is(10.5)))
                .andExpect(jsonPath("$.eatery.id", is(5)));

    }

    @Test
    @Order(70)
    void updateMenuItemWithDataIntegrityViolation() throws Exception {

        AtomicReference<Integer> menuItemId = new AtomicReference<>();
        menuItemRepository.findByName("Пункт меню №4").ifPresent(menuItemEntity -> menuItemId.set(menuItemEntity.getId()));

        MenuItemRequest menuItemRequest = new MenuItemRequest();
        menuItemRequest.setName("Пункт меню №3");
        menuItemRequest.setDate(LocalDate.of(2020, 5, 9));
        menuItemRequest.setPrice(50.0);

        mvc.perform(MockMvcRequestBuilders.put("/menu/" + menuItemId.get() + "?eateryId=5")
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemRequest)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]", is("Violated the integrity of the database")));

    }

    @Test
    @Order(80)
    void deleteMenuItem() throws Exception {

        AtomicReference<Integer> menuItemId = new AtomicReference<>();
        menuItemRepository.findByName("TestMenuItemUpdate").ifPresent(menuItemEntity -> menuItemId.set(menuItemEntity.getId()));

        mvc.perform(MockMvcRequestBuilders.delete("/menu/" + menuItemId.get())
                .header("Authorization", "GoTJSBA eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YW5lZy5ydUBnbWFpbC5jb20ifQ.TabCVlCuBai9OTodeEmR-s5A2ol5As7-YGKCxxWu2Sqfi9-5iiMfsBMfmsIGF8LlDGxRSRkEsISnPH_V5A1Utw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationResult", is("SUCCESS")));

    }

}