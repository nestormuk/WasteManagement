package com.usafi.waste_management_system.model;

import com.usafi.waste_management_system.util.EAccountStatus;
import com.usafi.waste_management_system.util.EUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users implements UserDetails {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private EAccountStatus accountStatus=EAccountStatus.PENDING;

    @Column(name = "verification_code")
    private int verificationCode;

    @Column(name = "verification_exp")
    private Date verificationExp;

    private EUserRole role = EUserRole.USER;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Payment> payments;


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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != EAccountStatus.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return  accountStatus == EAccountStatus.APPROVED;
    }
}
