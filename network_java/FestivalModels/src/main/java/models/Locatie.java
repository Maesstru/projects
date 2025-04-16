package models;

public class Locatie extends Entity<Long> {
    private String oras;
    private String Strada;
    private String numar;

    public Locatie(String oras, String strada, String numar) {
        this.oras = oras;
        Strada = strada;
        this.numar = numar;
    }

    public Locatie(Long id, String oras, String strada, String numar) {
        setId(id);
        this.oras = oras;
        Strada = strada;
        this.numar = numar;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getStrada() {
        return Strada;
    }

    public void setStrada(String strada) {
        Strada = strada;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }
}
