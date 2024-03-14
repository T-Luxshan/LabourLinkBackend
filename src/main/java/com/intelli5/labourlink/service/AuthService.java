package com.intelli5.labourlink.service;

import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserRole;
import com.intelli5.labourlink.repository.UserRepository;
import com.luxshan.authentication.auth.utils.AuthResponse;
import com.luxshan.authentication.auth.utils.LoginRequest;
import com.luxshan.authentication.auth.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest, UserRole role){
        User user;
        if(role == UserRole.CUSTOMER ){
             user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .mobileNumber(registerRequest.getMobileNumber())
                    .nic(registerRequest.getMobileNumber())
                    .role(UserRole.CUSTOMER)
                    .build();
        } else if (role == UserRole.ADMIN) {
            user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .mobileNumber(registerRequest.getMobileNumber())
                    .nic(registerRequest.getMobileNumber())
                    .role(UserRole.ADMIN)
                    .build();

        } else {
             user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .mobileNumber(registerRequest.getMobileNumber())
                    .nic(registerRequest.getMobileNumber())
                    .role(UserRole.CUSTOMER)
                    .build();
        }


        User savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

}
