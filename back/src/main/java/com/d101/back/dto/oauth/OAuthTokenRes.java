package com.d101.back.dto.oauth;

public interface OAuthTokenRes {

    default String getAccessToken() {
        return null;
    }

    default String getTokenType() {
        return null;
    }

    default String getRefreshToken() {
        return null;
    }

    default Long getExpiresIn() {
        return null;
    }

    default Long getRefreshTokenExpiresIn() {
        return null;
    }

    default String getScope() {
        return null;
    }
}