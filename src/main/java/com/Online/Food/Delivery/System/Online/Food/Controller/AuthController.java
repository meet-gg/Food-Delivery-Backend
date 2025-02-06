package com.Online.Food.Delivery.System.Online.Food.Controller;

import com.Online.Food.Delivery.System.Online.Food.DTO.LoginDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.LoginResponseDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.SignUpDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.UserDTO;
import com.Online.Food.Delivery.System.Online.Food.Repository.UserRepository;
import com.Online.Food.Delivery.System.Online.Food.Services.AuthServices;
import com.Online.Food.Delivery.System.Online.Food.Services.AuthServicesimpl;
import com.Online.Food.Delivery.System.Online.Food.Services.JwtServices;
import com.Online.Food.Delivery.System.Online.Food.Services.UserServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserServices userServices;
    private final AuthServices authServices;
//    private final SessionRepo sessionRepo;
    private final JwtServices jwtServices;
    private final UserRepository userRepo;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userServices.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authServices.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure(false); when use https production time it is true
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new RuntimeException("Refresh token not found inside cookie"));
//        log.info("{}",refreshToken);
        LoginResponseDTO loginResponseDTO=authServices.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO) ;
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//        String refreshToken = Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals("refreshToken"))
//                .findFirst()
//                .map(cookie -> cookie.getValue())
//                .orElseThrow(() -> new RuntimeException("Refresh token not found inside cookie"));
//        Session session=sessionRepo.findByRefreshToken(refreshToken).get();
//        Long id=session.getUser().getId();
//        String accessToken= jwtServices.generateAccessToken(userRepo.findById(id).get());
//        sessionRepo.delete(session);
//        Cookie cookie = new Cookie("refreshToken", "hello");
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false); // Set to true in production with HTTPS
//        response.addCookie(cookie);

//        return "loout";
//    }

}
