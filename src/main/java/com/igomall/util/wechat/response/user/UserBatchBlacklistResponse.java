package com.igomall.util.wechat.response.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igomall.util.wechat.response.BaseResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBatchBlacklistResponse extends BaseResponse {
}
