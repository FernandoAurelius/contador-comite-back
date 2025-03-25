package br.com.floresdev.contador_comite_back.domain.user;

import lombok.Getter;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    @Getter
    @SuppressWarnings("FieldMayBeFinal")
    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
