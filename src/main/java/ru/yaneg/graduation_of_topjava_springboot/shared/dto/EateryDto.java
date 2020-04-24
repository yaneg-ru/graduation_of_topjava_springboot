package ru.yaneg.graduation_of_topjava_springboot.shared.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EateryDto {
    private String id;
    @Size(min = 2, max = 100)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
