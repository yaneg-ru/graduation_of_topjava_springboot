package ru.yaneg.graduation_of_topjava_springboot.io.entitiy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MENU_ITEM", uniqueConstraints = {@UniqueConstraint(columnNames = {"MENU_ID", "NAME"}, name = "MENU_ITEMS_UNIQUE_MENU_NAME_IDX")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuItemEntity extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", nullable = false)
    @NotNull
    private MenuEntity menu;

    @Column(name = "PRICE", nullable = false)
    @NotNull
    private Double price;

    public MenuItemEntity() {
    }

    public MenuItemEntity(String name, double price) {
        super(name);
        this.price = price;
    }

    public MenuItemEntity(int id, MenuEntity menu, String name, double price) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    public MenuItemEntity(MenuEntity menu, String name, double price) {
        super(name);
        this.price = price;
        this.menu = menu;
    }

    public MenuItemEntity(MenuItemEntity menuItem) {
        super(menuItem.getId(), menuItem.getName());
        this.price = menuItem.getPrice();
        this.menu = menuItem.getMenu();
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
