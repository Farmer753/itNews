package ru.dpwg.itnews.domain.session;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("refresh_token")
    public String refreshToken;

    @SerializedName("access_token")
    public String accessToken;
}
