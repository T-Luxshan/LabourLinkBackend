package com.intelli5.labourlink.entity;

import ch.qos.logback.core.util.Loader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;




@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "[User]")
//@Builder
public  class User implements UserDetails {

    @Id
    @Column(unique = true,nullable = false)
    @Email(message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Field can not be empty")
    private String name;

    @NotBlank(message = "Field can not be empty")
    @Size(min = 5, message = "The password must have at least 5 characters")

    private String password;

    @NotBlank(message = "Field can not be empty")
    @Column(unique = true)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    UserRole role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.DETACH, orphanRemoval = true)
    private ForgotPassword forgotPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "ReportedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> ReportedByUser;

    @JsonIgnore
    @OneToMany(mappedBy = "ReportedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> ReportedToUser;

    private boolean isVerified = true;
    private boolean isEnabled = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private LocalDate joinDate= LocalDate.now();
//    private LocalTime joinTime=LocalTime.now();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    private Status status;



//   Todo need to check
//    public Status getStatus() {
//        return this.status != null ? this.status : Status.OFFLINE;
//    }

}
