package services;

import dtos.ConcertDto;

public interface IFestivalObserver {
    void updateBilete(ConcertDto concert) throws FestivalException;
}
