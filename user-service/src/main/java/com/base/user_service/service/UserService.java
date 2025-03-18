package com.base.user_service.service;

import com.base.user_service.entity.User;
import com.base.user_service.model.Bike;
import com.base.user_service.model.Car;
import com.base.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

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

}
