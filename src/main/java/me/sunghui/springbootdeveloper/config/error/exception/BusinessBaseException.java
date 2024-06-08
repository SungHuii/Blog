package me.sunghui.springbootdeveloper.config.error.exception;

import me.sunghui.springbootdeveloper.config.error.ErrorCode;

// 비즈니스 로직을 작성하다 발생하는 예외를 모아둘 최상위 클래스
// ex. 조회 대상이 없는 경우에 대한 예외를 정의하는 NotFoundException
// ex2. 블로그 글을 조회했을 때 발생 가능한 ArticleNotFoundException 등
public class BusinessBaseException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessBaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
