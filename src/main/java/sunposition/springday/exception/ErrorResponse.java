package sunposition.springday.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String message;
    private HttpStatus status;

    public ErrorResponse(
            final String errorMessage, final HttpStatus errorStatus) {
        this.message = errorMessage;
        this.status = errorStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
