package ru.yaneg.graduation_of_topjava_springboot.io.entitiy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "MENU_ITEM", uniqueConstraints = {@UniqueConstraint(columnNames = {"EATERY_ID", "DATA", "NAME"}, name = "MENU_ITEMS_UNIQUE_MENU_NAME_IDX")})
@Cache(region = "menuItem", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuItemEntity extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EATERY_ID", nullable = false)
    @NotNull
    private EateryEntity eatery;

    @Column(name = "DATA", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "PRICE", nullable = false)
    @NotNull
    private Double price;

    public MenuItemEntity() {
    }

    public MenuItemEntity(EateryEntity eatery, LocalDate date, String name, Double price) {
        super(name);
        this.eatery = eatery;
        this.date = date;
        this.price = price;
    }

    public EateryEntity getEatery() {
        return eatery;
    }

    public void setEatery(EateryEntity eatery) {
        this.eatery = eatery;
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
