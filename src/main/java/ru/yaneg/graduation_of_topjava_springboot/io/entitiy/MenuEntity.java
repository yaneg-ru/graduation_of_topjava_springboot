package ru.yaneg.graduation_of_topjava_springboot.io.entitiy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "MENU", uniqueConstraints = {@UniqueConstraint(columnNames = {"DATA", "EATERY_ID"}, name = "MENU_UNIQUE_DATE_EATERY_IDX")})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MenuEntity extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "EATERY_ID", nullable = false)
    @NotNull
    private EateryEntity eatery;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MenuItemEntity> menuItems;

    @Column(name = "DATA", nullable = false)
    @NotNull
    private LocalDate date;

    public MenuEntity() {
    }

    public MenuEntity(EateryEntity eatery, LocalDate date) {
        super();
        this.eatery = eatery;
        this.date = date;
    }

    public MenuEntity(int id, EateryEntity eatery, LocalDate date) {
        super(id);
        this.eatery = eatery;
        this.date = date;
    }

    public MenuEntity(MenuEntity menu) {
        super(menu.getId());
        this.setDate(menu.getDate());
        this.setEatery(menu.getEatery());
        this.setMenuItems(menu.getMenuItems());

    }

    public EateryEntity getEatery() {
        return eatery;
    }

    public void setEatery(EateryEntity eatery) {
        this.eatery = eatery;
    }

    public Set<MenuItemEntity> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItemEntity> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
