package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.LoginDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.LoginResponseDTO;

public interface AuthServices {
     LoginResponseDTO login(LoginDTO loginDTO);
     LoginResponseDTO refreshToken(String refreshToken);
}
