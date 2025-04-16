package persistence;

import models.Entity;

import java.util.Collection;
import java.util.List;

public interface IRepository<ID,T extends Entity<ID>> {

    T find(ID id);
    Collection<T> findAll();
    void save(T t) throws RepoException;
    void update(T t);
}
