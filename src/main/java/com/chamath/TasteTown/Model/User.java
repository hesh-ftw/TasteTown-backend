package com.chamath.TasteTown.Model;

import com.chamath.TasteTown.DTO.RestaurantDto;
import com.chamath.TasteTown.Enums.USER_ROLE;
import com.chamath.TasteTown.Enums.USER_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String password;

    private String email;

    private USER_ROLE role;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favourites = new ArrayList();

    //will remove all the addresses related to a particular user, when user deleted their account
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses= new ArrayList<>();

    private USER_STATUS status;
}
