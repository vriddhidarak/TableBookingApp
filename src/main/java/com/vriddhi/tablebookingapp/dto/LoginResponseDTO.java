package com.vriddhi.tablebookingapp.dto;


import com.vriddhi.tablebookingapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String sessionId;
    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private String userAddress;

    public static LoginResponseDTO mapToLoginResponseDTO(User user) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setUserName(user.getUserName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setUserAddress(user.getUserAddress());
        return responseDTO;
    }
}
