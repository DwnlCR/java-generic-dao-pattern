package ColectionsListaseArrays.dao;

import ColectionsListaseArrays.domain.GenericDomain;

import java.util.*;
import java.util.function.Predicate;

public abstract class GenericDAO< ID, T extends GenericDomain<ID>>{

    private final Map<ID, T> db = new LinkedHashMap<>();

    public T save(T domain){
        db.put(domain.getId(), domain);
        return domain;
    }

    @SafeVarargs
    public final boolean saveAll(T... domains){
        Arrays.stream(domains).forEach(d -> db.put(d.getId(), d));
        return true;
    }

    public boolean exists(ID id) {
        return db.containsKey(id);
    }

    public T update(ID id, T domain){
        if(!exists(id)) throw new EntityNotFoundException("Id " + id + " não encontrado");
        db.put(id, domain);
        return domain;
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(db.get(id));
    }

    public boolean deleteById(ID id){
        return db.remove(id) != null;
    }

    public boolean delete(T domain){
        return db.remove(domain.getId()) != null;
    }
    public Optional<T> find(Predicate<T> filterCallBack){
        return db.values().stream().filter(filterCallBack).findFirst();
    }
    public List<T> findAll(){
        return Collections.unmodifiableList(new ArrayList<>(db.values()));
    }
    public int count(){
        return db.size();
    }
}
