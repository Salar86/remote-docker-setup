package dk.lyngby.exception;

import java.time.LocalDateTime;

public class ApiException extends Exception{

    private final int statusCode;
    private final String timestamp;

    public ApiException(int statusCode, String message, String timestamp) {
        super(message);
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now().toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
