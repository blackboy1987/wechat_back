package com.igomall.entity.wechat.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.igomall.entity.wechat.response.BaseResponse;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken extends BaseResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private Date expiresDate;

    @JsonProperty("expires_in")
    private Long expires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
