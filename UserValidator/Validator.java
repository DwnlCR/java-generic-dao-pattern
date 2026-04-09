package dao_pattern.UserValidator;

import dao_pattern.Exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
