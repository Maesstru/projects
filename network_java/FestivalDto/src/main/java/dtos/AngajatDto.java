package dtos;

import models.Angajat;

import java.io.Serializable;

public class AngajatDto implements Serializable {
    private Long id;
    private String nume;
    private String password;

    public AngajatDto() {}

    public AngajatDto(Angajat angajat) {
        id = angajat.getId();
        nume = angajat.getUsername();
        password = angajat.getPassword();
    }

    public Long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPassword() {
        return password;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
