package com.sparta.dto.response;

import com.sparta.common.ErrorCode;
import lombok.Getter;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String message;
    private final ErrorCode errorCode;
    private final Map<String, String> validationErrors;

    public ErrorResponse(ErrorCode errorCode, Map<String, String> validationErrors) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
    }
}
