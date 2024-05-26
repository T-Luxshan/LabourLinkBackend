package com.intelli5.labourlink.entity;

import com.intelli5.labourlink.Enum.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
//@Builder
public  class User implements UserDetails {



    @Id
    @NotNull
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
    private UserRole role;
private LocalDate joinDate;
private LocalTime joinTime;
private boolean isPresent =true;
//    @Enumerated(EnumType.STRING)
//    UserRole role;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    private boolean isEnabled = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;

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

    public void setRole(com.intelli5.labourlink.entity.UserRole userRole) {
    }
}
