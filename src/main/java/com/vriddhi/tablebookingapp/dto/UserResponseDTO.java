package com.vriddhi.tablebookingapp.dto;

import com.vriddhi.tablebookingapp.exception.ResourceNotFoundException;
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

    public static UserResponseDTO mapToUserResponseDTO(User user) {
        if(user==null){
            throw new ResourceNotFoundException("User not found");
        }
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setUserName(user.getUserName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setUserAddress(user.getUserAddress());
        return responseDTO;
    }
}
