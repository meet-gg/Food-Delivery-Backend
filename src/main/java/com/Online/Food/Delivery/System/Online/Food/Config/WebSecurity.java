package com.Online.Food.Delivery.System.Online.Food.Config;

import com.Online.Food.Delivery.System.Online.Food.Entity.Enums.Role;
import com.Online.Food.Delivery.System.Online.Food.Filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.Online.Food.Delivery.System.Online.Food.Entity.Enums.Role.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true) //this is use for @Secured annonation use SECOND TYPE of security
//directly in the Controller with @Secured Annitation securedEnabled = true bydefault false hoy
public class WebSecurity {
    private final JwtAuthFilter jwtAuthFilter;
//    private final OAuth2SuccessHandler successHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("delivery/**","/auth/**","/home.html","/posts/**","/menu/**","restaurant/**","/delivery/**").permitAll()
                        .requestMatchers("/customer/**").hasAnyRole(USER.name())
                        .requestMatchers("/delivery-person/**").hasAnyRole(DELIVERY_BOY.name())
                        .requestMatchers("/restaurant/**").hasAnyRole(RESTAURANT.name())
                        .requestMatchers("/order/**").hasAnyRole(USER.name())
//                        .requestMatchers("").hasAnyRole(ADMIN.name())
//                        .requestMatchers("/posts/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyAuthority(POST_DELETE.name())
//                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAnyAuthority(POST_UPDATE.name())
//                        .requestMatchers(HttpMethod.GET,"/posts/**").hasAnyAuthority(POST_VIEW.name())
                        //this how set the authority
                         .anyRequest().authenticated())
                .csrf(csrf->csrf.disable())
                .sessionManagement(sessionConfig->sessionConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .oauth2Login(OAuth2LoginConfig -> OAuth2LoginConfig
//                        .failureUrl("/login?error=true")
//                        .successHandler(successHandler));
        return http.build();
    }
//    @Bean
//    UserDetailsService inMemoryUserDetailsService() {
//        UserDetails us1= User
//                .withUsername("mm")
//                .password(passwordEncoder().encode("mm123"))
//                .roles("USER")
//                .build();
//
//        UserDetails us2= User
//                .withUsername("meet")
//                .password(passwordEncoder().encode("meet123"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(us1,us2);
//    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
    }
}
