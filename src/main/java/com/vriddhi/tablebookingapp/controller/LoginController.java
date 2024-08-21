package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.service.LoginServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginServiceInterface loginService;

    @PostMapping
    @Operation(summary = "Login User")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO user = loginService.loginUser(loginRequestDTO);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }


}
