package com.chamath.TasteTown.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Embeddable
@Data
public class RestaurantDto {
    private Long id;
    private String title;
    private String description;

    @Column(length = 1000)
    private List<String>image;
}
