package com.backend.restapi.exception;

public class PropertyRoleNotFoundException extends RuntimeException {
    public PropertyRoleNotFoundException(String message) {
        super(message);
    }
}
