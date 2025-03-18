package com.base.user_service.controller;

import com.base.user_service.entity.User;
import com.base.user_service.model.Bike;
import com.base.user_service.model.Car;
import com.base.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") Long userId){

        User user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);

    }

    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") Long userId){

        User user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);

    }

}
