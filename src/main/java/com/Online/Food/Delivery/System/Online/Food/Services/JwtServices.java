package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.Entity.User;

import javax.crypto.SecretKey;

public interface JwtServices {
     SecretKey getSecretKey();
     String generateAccessToken(User user);
     String generateRefreshToken(User user);
     Long getUserIdFromToken(String token);
}
