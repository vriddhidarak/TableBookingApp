package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private String userAddress;
}
