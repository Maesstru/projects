package dtos;

import java.io.Serializable;

public class BiletDto implements Serializable {
    ConcertDto concert;
    int nrLocuri;


    public BiletDto(ConcertDto concert, int nrLocuri) {
        this.concert = concert;
        this.nrLocuri = nrLocuri;
    }

    public ConcertDto getConcert() {
        return concert;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }
}
