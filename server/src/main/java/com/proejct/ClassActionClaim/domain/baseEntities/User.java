package com.proejct.ClassActionClaim.domain.baseEntities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User extends DateTimeEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    private String authority;

    public User(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton((GrantedAuthority) () -> String.valueOf(authority));
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
