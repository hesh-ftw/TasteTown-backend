package com.chamath.TasteTown.Request;

import java.util.List;

public class CreateFoodRequest {
    private String name;
    private String description;
    private Float price;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }
}
