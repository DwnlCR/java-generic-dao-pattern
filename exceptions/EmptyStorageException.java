package dao_pattern.exceptions;

public class EmptyStorageException extends RuntimeException{
    public EmptyStorageException(String message) {
        super(message);
    }
}
