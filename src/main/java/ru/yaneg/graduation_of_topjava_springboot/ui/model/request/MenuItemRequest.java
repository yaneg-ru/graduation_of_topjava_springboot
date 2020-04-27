package ru.yaneg.graduation_of_topjava_springboot.ui.model.request;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MenuItemRequest {

    @NotNull
    private Integer eateryId;

    @NotNull
    private LocalDate date;

    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private Double price;

    public MenuItemRequest() {
    }

    public Integer getEateryId() {
        return eateryId;
    }

    public void setEateryId(Integer eateryId) {
        this.eateryId = eateryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
