package org.sharpsw.kraken.service;

public class SchemaLoaderException extends Exception {
    public SchemaLoaderException(String message) {
        super(message);
    }

    public SchemaLoaderException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
