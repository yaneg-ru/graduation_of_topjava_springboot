package ru.yaneg.graduation_of_topjava_springboot.ui.model.response;

import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.AbstractNamedEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

import java.time.LocalDate;

public class MenuItemShortResponse extends AbstractNamedEntity {

    private Double price;

    public MenuItemShortResponse() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
