package org.sharpsw.kraken.service;

public class SchemaLoaderException extends Exception {
	private static final long serialVersionUID = -3710890010681390292L;

	public SchemaLoaderException(String message) {
        super(message);
    }

    public SchemaLoaderException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
