package com.base.user_service.feignclients;

import com.base.user_service.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service")
// @RequestMapping("/cars") -> ya no se usa
public interface CarFeignClient {

    @PostMapping("/cars")
    Car saveCar(@RequestBody Car car);

    @GetMapping("/cars/byUser/{userId}")
    List<Car> getCars(@PathVariable("userId") Long userId);

}
