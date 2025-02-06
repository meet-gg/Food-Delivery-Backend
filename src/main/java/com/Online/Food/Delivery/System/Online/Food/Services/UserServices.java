package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.SignUpDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.UserDTO;

public interface UserServices {
    public UserDTO signup(SignUpDTO signUpDTO);
}
