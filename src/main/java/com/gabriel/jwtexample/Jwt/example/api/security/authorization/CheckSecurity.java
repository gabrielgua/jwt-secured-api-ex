package com.gabriel.jwtexample.Jwt.example.api.security.authorization;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Users {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.canListAll()")
        public @interface canListAll {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.canReadUser(#userId)")
        public @interface canReadUser {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.canEditUser(#userId)")
        public @interface canEditUser {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.canRemoveUser()")
        public @interface canRemoveUser {}
    }
}
