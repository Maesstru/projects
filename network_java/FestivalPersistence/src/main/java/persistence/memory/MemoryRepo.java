package persistence.memory;

import models.Entity;
import persistence.IRepository;
import persistence.RepoException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryRepo<ID,T extends Entity<ID>> implements IRepository<ID,T>{
    protected HashMap<ID,T> map;

    public MemoryRepo() {
        map = new HashMap<>();
    }

    @Override
    public T find(ID id) {
        return map.get(id);
    }

    @Override
    public Collection<T> findAll() {
        return map.values();
    }

    @Override
    public void save(T t) {
        if (map.containsKey(t.getId())) {
            throw new RepoException("Duplicate key");
        }

        map.put(t.getId(), t);
    }

    @Override
    public void update(T t) {
        if(!map.containsKey(t.getId())) {
            throw new RepoException("No such entity");
        }
        map.replace(t.getId(), t);
    }
}
