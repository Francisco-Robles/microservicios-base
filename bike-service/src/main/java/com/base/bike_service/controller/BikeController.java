package com.base.bike_service.controller;

import com.base.bike_service.entity.Bike;
import com.base.bike_service.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    @Autowired
    BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll() {
        List<Bike> bikes = bikeService.getAll();
        if (bikes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikesById(@PathVariable("id") int id) {
        Bike bike = bikeService.getBikeById(id);
        if (bike == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bike);
    }

    @PostMapping()
    public ResponseEntity<Bike> saveBike(@RequestBody Bike bike) {
        Bike savedBike = bikeService.save(bike);
        return ResponseEntity.ok(savedBike);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") Long userId) {
        List<Bike> bikes = bikeService.getBikesByUserId(userId);
        if (bikes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikes);
    }

}
