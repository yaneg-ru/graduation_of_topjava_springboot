package ru.yaneg.graduation_of_topjava_springboot.ui.model.response;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EateryResponse {
    private Integer id;
    @Size(min = 2, max = 100)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
