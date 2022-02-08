package ru.javaSchoolProject.security.userData;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.javaSchoolProject.models.User;


import java.util.Collection;
import java.util.Collections;

// Entity for spring security so in easy words just my User class but for Spring Security
public class MyUserDetails implements UserDetails{

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static MyUserDetails fromUserEntityToMyUserDetails(User userEntity) {
        MyUserDetails c = new MyUserDetails();
        c.login = userEntity.getLogin();
        c.password = userEntity.getPassword();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().toString()));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
