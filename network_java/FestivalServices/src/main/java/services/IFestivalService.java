package services;

import dtos.AngajatDto;
import dtos.ConcertDto;

import java.util.Collection;

public interface IFestivalService {

    void login(String username, String password,IFestivalObserver client) throws FestivalException;
    void logout(AngajatDto angajat) throws FestivalException;
    Collection<ConcertDto> getConcerts() throws FestivalException;
    void sellTickets(ConcertDto concert, int nrBilete) throws FestivalException;

}
