package me.sunghui.springbootdeveloper.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 에러 코드를 한 곳에 모아 관리하기 위한 enum
// 에러가 발생했을 때 어떤 HTTP 상태값으로 응답하는지, 어떤 기본 메시지를 가지고 있는지,
// 어떤 고유한 에러 코드를 가지는지를 정의
// 예외를 한 곳에서 관리할 수 있음
@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E1", "올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E2", "잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E3", "서버 에러가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E4", "존재하지 않는 엔티티입니다."),

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "A1", "존재하지 않는 아티클입니다.");

    private final String message;

    private final String code;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
