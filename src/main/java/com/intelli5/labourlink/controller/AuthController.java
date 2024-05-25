package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.Exception.CustomerRegistrationException;
import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.RefreshToken;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserRole;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.service.AuthService;
import com.intelli5.labourlink.service.JwtService;
import com.intelli5.labourlink.service.RefreshTokenService;
import com.intelli5.labourlink.utils.AuthResponse;
import com.intelli5.labourlink.utils.LoginRequest;
import com.intelli5.labourlink.utils.RefreshTokenRequest;
import com.intelli5.labourlink.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final CustomerRepository customerRepository;


    public AuthController(AuthService authService, JwtService jwtService, RefreshTokenService refreshTokenService,

                          CustomerRepository customerRepository) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerAdmin(registerRequest));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(@RequestBody RegisterRequest registerRequest) throws CustomerRegistrationException {
        return ResponseEntity.ok(authService.registerCustomer(registerRequest));
    }

    @PostMapping("/register/labour")
    public ResponseEntity<AuthResponse> registerLabour(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerLabour(registerRequest));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loginAdmin(loginRequest));
    }

    @PostMapping("/login/customer")
    public ResponseEntity<AuthResponse> loginCustomer(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loginCustomer(loginRequest));
    }

    @PostMapping("/login/labour")
    public ResponseEntity<AuthResponse> loginLabour(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loginLabour(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
    @GetMapping("/getRole/{email}")
    public ResponseEntity<UserRole> getCustomerByEmail(@PathVariable String email ){
        Optional<User> user = customerRepository.findByEmail(email);
        return ResponseEntity.ok(user.get().getRole());
    }
    @GetMapping("/getJobRoles")
    public List<String> getJobRoles() {
        return Arrays.stream(JobRole.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
