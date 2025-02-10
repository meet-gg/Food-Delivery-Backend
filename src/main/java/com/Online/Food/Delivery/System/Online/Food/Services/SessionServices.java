package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.Entity.User;

public interface SessionServices {
    void generateNewSession(User user, String refreshToken);
    void validateSession(String refreshToken);
}
