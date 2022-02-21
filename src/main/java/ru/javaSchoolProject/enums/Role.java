package ru.javaSchoolProject.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
