package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.DTO.SignUpDTO;
import com.Online.Food.Delivery.System.Online.Food.DTO.UserDTO;
import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import com.Online.Food.Delivery.System.Online.Food.Exceptions.ResourceNotFoundException;
import com.Online.Food.Delivery.System.Online.Food.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServices implements UserDetailsService {
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not found with mail "+username));
    }

    public UserDTO signup(SignUpDTO signUpDTO) {
        Optional<User> user=userRepo.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()) {
            throw new BadCredentialsException("Email already in use");
        }
        User createUser=modelMapper.map(signUpDTO, User.class);
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));

        User saveUser = userRepo.save(createUser);
        return modelMapper.map(saveUser, UserDTO.class);
    }
}
