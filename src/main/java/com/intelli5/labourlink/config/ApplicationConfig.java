package com.intelli5.labourlink.config;

import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.AdminRepository;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class ApplicationConfig {


    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;

    public ApplicationConfig( AdminRepository adminRepository, CustomerRepository customerRepository, LabourRepository labourRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        return username -> userRepository.findByEmail(username) // findByEmail was findByUsername
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email "+ username));
//
//    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            // Check admin repository for the user
            Optional<User> admin = adminRepository.findByEmail(username);
            if (admin.isPresent()) {
                return admin.get();
            }

            // Check customer repository for the user
            Optional<User> customer = customerRepository.findByEmail(username);
            if (customer.isPresent()) {
                return customer.get();
            }

            // Check labour repository for the user
            Optional<User> labour = labourRepository.findByEmail(username);
            if (labour.isPresent()) {
                return labour.get();
            }

            // If none found, throw exception
            throw new UsernameNotFoundException("User not found with email "+ username);
        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Exception added to the method signature.
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
