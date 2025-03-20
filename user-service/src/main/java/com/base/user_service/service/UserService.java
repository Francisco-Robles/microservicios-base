package com.base.user_service.service;

import com.base.user_service.entity.User;
import com.base.user_service.feignclients.BikeFeignClient;
import com.base.user_service.feignclients.CarFeignClient;
import com.base.user_service.model.Bike;
import com.base.user_service.model.Car;
import com.base.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    public List<Car> getCars(Long userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/cars/byUser/" + userId, List.class);
        return cars;
    }

    public List<Bike> getBikes(Long userId){
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bikes/byUser/" + userId, List.class);
        return bikes;
    }

    public Car saveCar (Long userId, Car car){

        car.setUserId(userId);
        Car newCar = carFeignClient.saveCar(car);
        return newCar;

    }

    public Bike saveBike (Long userId, Bike bike){

        bike.setUserId(userId);
        Bike newBike = bikeFeignClient.saveBike(bike);
        return newBike;

    }

    public Map<String, Object> getUserAndVehicles(Long userId){

        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            result.put("Mensaje:", "No existe el usuario.");
            return result;
        }
        result.put("User:", user);

        List<Car> cars = carFeignClient.getCars(userId);
        if (cars == null) //No uso 'isEmpty' porque la lista no lo toma, el resultado de la run era que cars = null
            result.put("Mensaje:", "Este usuario no tiene autos.");
        else
            result.put("Cars:", cars);

        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if (bikes == null) //Lo mismo que en la linea 83 pero con bikes
            result.put("Mensaje:", "Este usuario no tiene motos.");
        else
            result.put("Bikes:", bikes);

        return result;
    }

}
