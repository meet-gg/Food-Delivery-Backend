package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.LoginDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.LoginResponseDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServices {
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
    private final UserRepository userRepo;
//    private final SessionServices sessionServices;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        User user = (User) authenticate.getPrincipal();
        String accessToken = jwtServices.generateAccessToken(user);
        String refreshToken = jwtServices.generateRefreshToken(user);
//        sessionServices.generateNewSession(user, refreshToken);

        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
//        sessionServices.validateSession(refreshToken);
        Long userId=jwtServices.getUserIdFromToken(refreshToken);
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        String accessToken = jwtServices.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

}
