package ru.yaneg.graduation_of_topjava_springboot.ui.model.response;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.AbstractNamedEntity;
import ru.yaneg.graduation_of_topjava_springboot.io.entitiy.EateryEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MenuItemResponse extends AbstractNamedEntity {

    private Integer eateryId;

    private LocalDate date;

    private Double price;

    public MenuItemResponse() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
