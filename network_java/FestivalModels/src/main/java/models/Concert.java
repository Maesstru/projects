package models;

import java.time.LocalDateTime;

public class Concert extends Entity<Long> {
    private LocalDateTime dateTime;
    private Long locatie;
    private Long artist;
    private int disponibile;
    private int ocupate;

    public Concert(LocalDateTime dateTime, Long locatie, Long artist, int disponibile, int ocupate) {
        this.dateTime = dateTime;
        this.locatie = locatie;
        this.artist = artist;
        this.disponibile = disponibile;
        this.ocupate = ocupate;
    }

    public Concert(Long id, LocalDateTime dateTime, Long locatie, Long artist, int disponibile, int ocupate) {
        setId(id);
        this.dateTime = dateTime;
        this.locatie = locatie;
        this.artist = artist;
        this.disponibile = disponibile;
        this.ocupate = ocupate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getLocatie() {
        return locatie;
    }

    public void setLocatie(Long locatie) {
        this.locatie = locatie;
    }

    public Long getArtist() {
        return artist;
    }

    public void setArtist(Long artist) {
        this.artist = artist;
    }

    public int getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(int disponibile) {
        this.disponibile = disponibile;
    }

    public int getOcupate() {
        return ocupate;
    }

    public void setOcupate(int ocupate) {
        this.ocupate = ocupate;
    }
}
