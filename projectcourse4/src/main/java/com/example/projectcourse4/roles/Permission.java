package com.example.projectcourse4.roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    SUPPLIER_READ("supplier:read"),
    SUPPLIER_UPDATE("supplier:update"),
    SUPPLIER_CREATE("supplier:create"),
    SUPPLIER_DELETE("supplier:delete");


    @Getter
    private final String permission;
}
