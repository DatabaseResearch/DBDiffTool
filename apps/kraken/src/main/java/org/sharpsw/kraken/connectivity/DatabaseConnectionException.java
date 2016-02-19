package org.sharpsw.kraken.connectivity;

public class DatabaseConnectionException extends Exception {
	private static final long serialVersionUID = -5134503633794557986L;

	public DatabaseConnectionException(String message, Throwable exception) {
        super(message, exception);
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}
