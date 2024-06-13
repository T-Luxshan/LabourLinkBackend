package com.intelli5.labourlink.service;

import com.intelli5.labourlink.repository.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import com.intelli5.labourlink.entity.RefreshToken;
import com.intelli5.labourlink.entity.User;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;
    private final AdminRepository adminRepository;


    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               CustomerRepository customerRepository,
                               LabourRepository labourRepository,
                               AdminRepository adminRepository) {
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
        this.adminRepository = adminRepository;
//        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshTokenCustomer(String username) {
        User user = customerRepository.findByEmail(username) // findByEmail was findByUsername
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found with email " + username));
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            long refreshTokenValidity = 7*24*60*60*1000; //7 days;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);


        }
        return refreshToken;
    }

    public RefreshToken createRefreshTokenLabour(String username) {
        User user = labourRepository.findByEmail(username) // findByEmail was findByUsername
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found with email " + username));
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            long refreshTokenValidity = 7*24*60*60*1000; //7 days;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);


        }
        return refreshToken;
    }

    public RefreshToken createRefreshTokenAdmin(String username) {
        User user = adminRepository.findByEmail(username) // findByEmail was findByUsername
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found with email " + username));
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            long refreshTokenValidity = 7*24*60*60*1000; //7 days;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);


        }
        return refreshToken;
    }
    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if(refToken.getExpirationTime().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refToken;
    }

}
