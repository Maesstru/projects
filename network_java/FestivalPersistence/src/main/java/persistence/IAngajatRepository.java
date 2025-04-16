package persistence;

import models.Angajat;

public interface IAngajatRepository extends IRepository<Long, Angajat>{
    Angajat findByCredentials(String username, String password);
}
