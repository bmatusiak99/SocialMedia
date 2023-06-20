package com.reddit.domain;

public enum Role {
    USER, ADMIN;

    public String getAuthority() {
        return name();
    }
}

