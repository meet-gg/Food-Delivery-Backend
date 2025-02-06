package com.Online.Food.Delivery.System.Online.Food.Advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<String> otherError;

    public ApiError(HttpStatus status,String message, String otherErrorMessage) {
        this.httpStatus = status;
        this.message = message;
        this.otherError = List.of(otherErrorMessage);
    }
}
