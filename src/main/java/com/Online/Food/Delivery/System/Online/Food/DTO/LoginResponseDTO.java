package com.Online.Food.Delivery.System.Online.Food.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
