package com.base.car_service.service;

import com.base.car_service.entity.Car;
import com.base.car_service.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getUserById(int id){
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        Car newCar = carRepository.save(car);
        return newCar;
    }

    public List<Car> getCarsByUserId(Long id){
        return carRepository.findByUserId(id);
    }

}
