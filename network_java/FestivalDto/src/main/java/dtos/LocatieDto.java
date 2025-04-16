package dtos;

import models.Locatie;

import java.io.Serializable;

public class LocatieDto implements Serializable {
    private Long idLocatie;
    private String oras;
    private String strada;
    private String numar;

    public LocatieDto() {}

    public LocatieDto(Locatie locatie) {
        idLocatie = locatie.getId();
        oras = locatie.getOras();
        strada = locatie.getStrada();
        numar = locatie.getNumar();
    }

    public Long getIdLocatie() {
        return idLocatie;
    }

    public String getOras() {
        return oras;
    }

    public String getStrada() {
        return strada;
    }

    public String getNumar() {
        return numar;
    }

    @Override
    public String toString() {
        return oras + " | " + strada + " | " + numar;
    }
}
