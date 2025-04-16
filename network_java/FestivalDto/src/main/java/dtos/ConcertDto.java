package dtos;

import models.Concert;
import models.Artist;
import models.Locatie;


import java.io.Serializable;
import java.time.LocalDateTime;

public class ConcertDto implements Serializable {

    private ArtistDto artist;
    private Long cId;
    private LocalDateTime concertDate;
    private LocatieDto locatie;
    private int disponibile;
    private int ocupate;

    public String getArtist() {
        return artist.getName();
    }

    public void setArtist(Artist artist) {
        this.artist = new ArtistDto(artist);
    }

    public Long getcId() {
        return cId;
    }

    public LocalDateTime getConcertDate() {
        return concertDate;
    }

    public LocatieDto getLocatie() {
        return locatie;
    }

    public void setLocatie(Locatie locatie) {
        this.locatie = new LocatieDto(locatie);
    }

    public int getDisponibile() {
        return disponibile;
    }

    public int getOcupate() {
        return ocupate;
    }

    public static ConcertDto fromConcert(Concert c) {
        ConcertDto dto = new ConcertDto();
        dto.disponibile = c.getDisponibile();
        dto.ocupate = c.getOcupate();
        dto.concertDate = c.getDateTime();
        dto.cId = c.getId();

        return dto;
    }

    @Override
    public String toString() {
        return artist.getName() + " | " + locatie.toString() + " | " + disponibile;
    }

    public void setDisponibile(int i) {
        disponibile = i;
    }

    public void setOcupate(int i) {
        ocupate = i;
    }
}
