package lk.hmpb.course.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final int errorCode;
    @Getter
    private final HttpStatus status;

    public AppException(int errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public AppException(int errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

}
