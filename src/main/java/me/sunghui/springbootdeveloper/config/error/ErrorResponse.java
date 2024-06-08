package me.sunghui.springbootdeveloper.config.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// ErrorAttributes를 대체할 에러 메시지용 객체
// 에러 메시지가 포함된 message 필드와 고유 에러 코드인 code 필드를 가지고 있음
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private String code;

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.code = code.getCode();
    }

    public ErrorResponse(final ErrorCode code, final String message) {
        this.message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }


}
