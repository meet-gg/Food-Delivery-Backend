package com.Online.Food.Delivery.System.Online.Food.Services;

public interface MailServices {
    void sendMail(String to, String subject, String text);
}
