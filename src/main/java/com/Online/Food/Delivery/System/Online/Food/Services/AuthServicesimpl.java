package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.LoginDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.LoginResponseDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServicesimpl implements AuthServices {
    private final AuthenticationManager authenticationManager;
    private final JwtServicesImpl jwtServicesImpl;
    private final UserRepository userRepo;
    private final MailServices mailServices;
    private final SessionServices sessionServices;
    @Override
    @CachePut(cacheNames = "Customer-login",key = "#result.userId")
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        User user = (User) authenticate.getPrincipal();
        String accessToken = jwtServicesImpl.generateAccessToken(user);
        String refreshToken = jwtServicesImpl.generateRefreshToken(user);
        sessionServices.generateNewSession(user, refreshToken);
        mailServices.sendMail(user.getEmail(),"Zomato Clone","you are logging");
        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }
    @Override
    public LoginResponseDTO refreshToken(String refreshToken) {
        sessionServices.validateSession(refreshToken);
        Long userId= jwtServicesImpl.getUserIdFromToken(refreshToken);
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        String accessToken = jwtServicesImpl.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

}
