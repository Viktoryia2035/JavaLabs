package sunposition.springday.exception;

public class HttpErrorExceptions {

    private HttpErrorExceptions() {
    }

    public static class CustomNotFoundException extends RuntimeException {
        public CustomNotFoundException(String message) {
            super(message);
        }
    }

    public static class CustomBadRequestException extends RuntimeException {
        public CustomBadRequestException(String message) {
            super(message);
        }
    }

    public static class CustomMethodNotAllowedException extends RuntimeException {
        public CustomMethodNotAllowedException(String message) {
            super(message);
        }
    }

    public static class CustomServiceUnavailableException extends RuntimeException {
        public CustomServiceUnavailableException(String message) {
            super(message);
        }
    }

    public static class CustomInternalServerErrorException extends RuntimeException {
        public CustomInternalServerErrorException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
