package com.base.user_service.feignclients;

import com.base.user_service.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service")
public interface BikeFeignClient {

    @PostMapping("/bikes")
    Bike saveBike(@RequestBody Bike bike);

    @GetMapping("/bikes/byUser/{userId}")
    List<Bike> getBikes (@PathVariable("userId") Long userId);

}
