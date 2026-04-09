package dao_pattern.uservalidator;

import dao_pattern.exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
