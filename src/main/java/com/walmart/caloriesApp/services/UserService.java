package com.walmart.caloriesApp.services;

import com.walmart.caloriesApp.dtos.UserDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    List<String> addUser(UserDto userDto);
    @Transactional
    List<String> userLogin(UserDto userDto);
}
