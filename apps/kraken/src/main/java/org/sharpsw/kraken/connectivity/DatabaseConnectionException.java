package org.sharpsw.kraken.connectivity;

public class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message, Throwable exception) {
        super(message, exception);
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}
