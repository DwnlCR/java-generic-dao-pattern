package dao_pattern.Exceptions;

public class ValidatorException extends RuntimeException{
    public ValidatorException(String message) {
        super(message);
    }
}
