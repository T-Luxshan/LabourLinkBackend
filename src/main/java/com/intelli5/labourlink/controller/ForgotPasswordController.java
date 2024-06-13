package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.MailBody;
import com.intelli5.labourlink.entity.ForgotPassword;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.entity.UserRole;
import com.intelli5.labourlink.repository.*;
import com.intelli5.labourlink.service.EmailService;
import com.intelli5.labourlink.utils.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {


    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    public ForgotPasswordController(AdminRepository adminRepository,
                                    CustomerRepository customerRepository,
                                    LabourRepository labourRepository,
                                    EmailService emailService,
                                    ForgotPasswordRepository forgotPasswordRepository,
                                    PasswordEncoder passwordEncoder) {

        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/customerExist/{email}")
    public ResponseEntity<Boolean> isCustomerExist(@PathVariable String email){
        Optional<User> customer = customerRepository.findByEmail(email);
        if (customer.isPresent())
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.ok(false);
    }

    @PostMapping("/verifyMail/{role}/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable UserRole role, @PathVariable String email){
        User user = switch (role) {
            case CUSTOMER -> customerRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Provide a valid email"));
            case ADMIN -> adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Provide a valid email"));
            case LABOUR -> labourRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Provide a valid email"));
        };

        try{
            ForgotPassword oldFp = forgotPasswordRepository.FindByUser(user)
                    .orElseThrow(() -> new RuntimeException("Invalid OTP for " + email));
            forgotPasswordRepository.deleteById(oldFp.getFpid());

        }catch (Exception e){
            System.out.println("Email did not exist earlier");
        }

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is your OTP for your forgot password request : " + otp)
                .subject("OTP for forget password")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date((System.currentTimeMillis() + 90 * 1000)))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOtp/{role}/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable UserRole role, @PathVariable Integer otp, @PathVariable String email){

        User user = switch (role) {
            case CUSTOMER -> customerRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Provide a valid email"));
            case ADMIN -> adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Provide a valid email"));
            case LABOUR -> labourRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Provide a valid email"));
        };

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for " + email));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/changePassword/{role}/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email, @PathVariable UserRole role){

        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Password does not match", HttpStatus.EXPECTATION_FAILED);
        }
        String encodePassword = passwordEncoder.encode(changePassword.password());
        switch (role){
            case ADMIN:
                adminRepository.updatePassword(email, encodePassword);
                break;
            case LABOUR:
                labourRepository.updatePassword(email, encodePassword);
                break;
            case CUSTOMER:
                customerRepository.updatePassword(email, encodePassword);
                break;

        }

        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}

