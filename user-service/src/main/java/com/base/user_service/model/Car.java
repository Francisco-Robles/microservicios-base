package com.base.user_service.model;

import lombok.*;

//@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private String brand;
    private String model;
    private Long userId;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
