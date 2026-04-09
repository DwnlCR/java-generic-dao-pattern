package dao_pattern.dao;

import dao_pattern.domain.UserDomain;

public class UserDAO extends GenericDAO<Integer, UserDomain> {
    private int nextId = 1;

    @Override
    public UserDomain save(UserDomain domain){
        domain.setId(nextId++);
        return super.save(domain);
    }
}
