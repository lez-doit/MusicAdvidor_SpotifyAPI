package org.example.model.error;

public class ServerError extends RuntimeException{
    public ServerError(String message) {
        super(message);
    }
}
