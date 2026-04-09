package dao_pattern.uservalidator;

import dao_pattern.exceptions.ValidatorException;
import dao_pattern.domain.UserDomain;

public class UserDomainValidator implements Validator<UserDomain> {
    @Override
    public void validate(UserDomain user) throws ValidatorException {
        if (user.getName() == null || user.getName().isBlank())
            throw new ValidatorException("Informe um nome válido");
        if (user.getName().length() < 2)
            throw new ValidatorException("Nome deve ter pelo menos 2 letras");
        if (user.getAge() <= 0 || user.getAge() > 120)
            throw new ValidatorException("Idade deve ser entre 1 e 120");
    }
}