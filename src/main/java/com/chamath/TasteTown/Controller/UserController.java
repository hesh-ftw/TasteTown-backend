package com.chamath.TasteTown.Controller;

import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> loadUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
