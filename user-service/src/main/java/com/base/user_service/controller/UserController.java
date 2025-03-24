package com.base.user_service.controller;

import com.base.user_service.entity.User;
import com.base.user_service.model.Bike;
import com.base.user_service.model.Car;
import com.base.user_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") Long userId){

        User user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);

    }
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/saveCar/{userId}")
    public ResponseEntity<Car> saveCar (@PathVariable("userId") Long userId, @RequestBody Car car){

        if (userService.getUserById(userId) == null)
            return ResponseEntity.notFound().build();
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(car);

    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") Long userId){

        User user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);

    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackSaveBike")
    @PostMapping("/saveBike/{userId}")
    public ResponseEntity<Bike> saveBike (@PathVariable("userId") Long userId, @RequestBody Bike bike){

        if (userService.getUserById(userId) == null)
            return ResponseEntity.notFound().build();
        Bike newBike = userService.saveBike(userId, bike);
        return ResponseEntity.ok(bike);

    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
    @GetMapping("/getAll/{userId}")
    ResponseEntity<Map<String, Object>> getAllVehiclesByUserId (@PathVariable("userId") Long userId){

        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);

    }

    private ResponseEntity<String> fallbackGetCars(@PathVariable("userId") Long userId, RuntimeException e){

        return new ResponseEntity<>("El usuario " + userId + " tiene los autos en el taller.", HttpStatus.OK);

    }

    private ResponseEntity<String> fallbackSaveCar(@PathVariable("userId") Long userId, @RequestBody Car car, RuntimeException e){

        return new ResponseEntity<>("El usuario " + userId + " no tiene dinero para autos.", HttpStatus.OK);

    }

    private ResponseEntity<String> fallbackGetBikes(@PathVariable("userId") Long userId, RuntimeException e){

        return new ResponseEntity<>("El usuario " + userId + " tiene las motos en el taller.", HttpStatus.OK);

    }

    private ResponseEntity<String> fallbackSaveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike, RuntimeException e){

        return new ResponseEntity<>("El usuario " + userId + " no tiene dinero para motos.", HttpStatus.OK);

    }

    private ResponseEntity<String> fallbackGetAll(@PathVariable("userId") Long userId, RuntimeException e){

        return new ResponseEntity<>("El usuario " + userId + " tiene los vehículos en el taller.", HttpStatus.OK);

    }



}
