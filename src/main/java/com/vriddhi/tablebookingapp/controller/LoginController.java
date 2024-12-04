package com.vriddhi.tablebookingapp.controller;

import com.vriddhi.tablebookingapp.dto.LoginRequestDTO;
import com.vriddhi.tablebookingapp.dto.LoginResponseDTO;
import com.vriddhi.tablebookingapp.dto.UserResponseDTO;
import com.vriddhi.tablebookingapp.service.LoginServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginServiceInterface loginService;

    @PostMapping
    @Operation(summary = "Login User")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        LoginResponseDTO user = loginService.loginUser(loginRequestDTO);
        if (user != null) {
            String sessionToken = session.getId();
            log.info("Session token: {}", sessionToken);
            user.setSessionId(sessionToken);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }


}
