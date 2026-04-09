package dao_pattern.Exceptions;

public class EmptyStorageException extends RuntimeException{
    public EmptyStorageException(String message) {
        super(message);
    }
}
