package com.vriddhi.tablebookingapp.service;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;

public interface LoginServiceInterface {
    UserResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
}
