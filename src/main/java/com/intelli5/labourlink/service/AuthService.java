package com.intelli5.labourlink.service;

import com.intelli5.labourlink.Exception.CustomerRegistrationException;
import com.intelli5.labourlink.entity.*;
import com.intelli5.labourlink.repository.AdminRepository;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.utils.AuthResponse;
import com.intelli5.labourlink.utils.LoginRequest;
import com.intelli5.labourlink.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService{
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;
    private final AdminRepository adminRepository;
    private final SuspendUserService suspendUserService;
    @Autowired
    private final NotificationAdminService notificationAdminService;

    public AuthResponse registerCustomer(RegisterRequest registerRequest) throws CustomerRegistrationException {
        Optional<SuspendUser> suspendedUser=suspendUserService.findByEmail(registerRequest.getEmail());
        if(!suspendedUser.isPresent()){
        try{
            var user = new Customer();
            user.setEmail(registerRequest.getEmail());
            user.setName(registerRequest.getName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setMobileNumber(registerRequest.getMobileNumber());
            user.setAddress(registerRequest.getAddress());
            user.setRole(UserRole.CUSTOMER);
            user.setStatus(Status.OFFLINE);
            user.setJoinDate(LocalDate.now());
            //user.setJoinTime(LocalTime.now());

            User savedUser = customerRepository.save(user);
            var accessToken = jwtService.generateToken(savedUser);
            var refreshToken = refreshTokenService.createRefreshTokenCustomer(savedUser.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
        catch (DataIntegrityViolationException ex) {

            throw new CustomerRegistrationException("Customer registration failed: " + ex.getMessage());
        } }
        else{
            throw new RuntimeException("Suspended user");}
    }

    public AuthResponse registerLabour(RegisterRequest registerRequest){
        Optional<SuspendUser> suspendedUser=suspendUserService.findByEmail(registerRequest.getEmail());
        if(suspendedUser.isEmpty()){
        var user = new Labour();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setMobileNumber(registerRequest.getMobileNumber());
        user.setNic(registerRequest.getNic());
        user.setRole(UserRole.LABOUR);
        user.setVerified(false);
        user.setJobRole(registerRequest.getJobRole());
        user.setDocumentUri(registerRequest.getDocumentUri());
        user.setJoinDate(LocalDate.now());
        //user.setJoinTime(LocalTime.now());
        User savedUser = labourRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshTokenCustomer(savedUser.getEmail());
            // Notify the admin
        NotificationAdmin userDetail =new NotificationAdmin();
               userDetail.setName(user.getName());
               userDetail.setEmail(user.getEmail());
               userDetail.setDocumentUri(user.getDocumentUri());
               userDetail.setJobRole(user.getJobRole().toString());
               userDetail.setJoinDate(user.getJoinDate().toString());
        notificationAdminService.notifyAdmin(userDetail);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }else{
            throw new RuntimeException("Suspended user");}
    }

    public AuthResponse registerAdmin(RegisterRequest registerRequest){

        var user = new Admin();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setMobileNumber(registerRequest.getMobileNumber());
        user.setCompanyId(registerRequest.getCompanyId());
        user.setRole(UserRole.ADMIN);

        log.info("user is"+user.getRole().toString());


        User savedUser = adminRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshTokenCustomer(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    public AuthResponse loginAdmin(LoginRequest loginRequest){

        log.info("loginAdmin , in service");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw e; // Or handle accordingly
        }

        var user = adminRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        var accessToken = jwtService.generateToken(user);
        log.info(accessToken);
        var refreshToken = refreshTokenService.createRefreshTokenAdmin(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


    public AuthResponse loginCustomer(LoginRequest loginRequest) {
        Optional<SuspendUser> suspendedUser = suspendUserService.findByEmail(loginRequest.getEmail());
        if (!suspendedUser.isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            var user = customerRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            var accessToken = jwtService.generateToken(user);
            var refreshToken = refreshTokenService.createRefreshTokenCustomer(loginRequest.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        } else {
            throw new RuntimeException("Suspended user");
        }
    }

    public AuthResponse loginLabour(LoginRequest loginRequest){
        Optional<SuspendUser> suspendedUser = suspendUserService.findByEmail(loginRequest.getEmail());
        if (!suspendedUser.isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            var user = labourRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            var accessToken = jwtService.generateToken(user);
            var refreshToken = refreshTokenService.createRefreshTokenLabour(loginRequest.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
        else {
            throw new RuntimeException("Suspended user");
        }
        }

    public boolean checkNicExists(String nic) {
        return labourRepository.existsByNic(nic);
    }

}
