package festival.server;

import dtos.AngajatDto;
import dtos.ConcertDto;
import models.Angajat;
import models.Concert;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import persistence.IAngajatRepository;
import persistence.IArtistRepository;
import persistence.IConcertRepository;
import persistence.ILocatieRepository;
import services.FestivalException;
import services.IFestivalObserver;
import services.IFestivalService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FestivalServicesImp implements IFestivalService {

    ILocatieRepository locatieRepository;
    IArtistRepository artistRepository;
    IConcertRepository concertRepository;
    IAngajatRepository angajatRepository;

    private Map<String, IFestivalObserver> loggedClients = new ConcurrentHashMap<>();


    private static Logger logger = LogManager.getLogger(FestivalServicesImp.class);

    public FestivalServicesImp(ILocatieRepository locatieRepository, IArtistRepository artistRepository, IConcertRepository concertRepository, IAngajatRepository angajatRepository) {
        this.locatieRepository = locatieRepository;
        this.artistRepository = artistRepository;
        this.concertRepository = concertRepository;
        this.angajatRepository = angajatRepository;
    }

    @Override
    public void login(String username, String password,IFestivalObserver client) throws FestivalException {
        Angajat found = angajatRepository.findByCredentials(username, password);
        if (found == null) {
            throw new FestivalException("Invalid username or password");
        }
        if(loggedClients.containsKey(found.getUsername())) {
            throw new FestivalException("User already logged in");
        }
        loggedClients.put(username, client);
    }

    @Override
    public void logout(AngajatDto angajat) throws FestivalException {
        if(angajat == null) {
            throw new FestivalException("Client object is null");
        }

        loggedClients.remove(angajat.getNume());
        logger.info(angajat.getNume() + " logged out");
    }

    @Override
    public Collection<ConcertDto> getConcerts() throws FestivalException {
        return concertRepository.findAll()
                .stream().map(c -> {
                    ConcertDto dto = ConcertDto.fromConcert(c);
                    dto.setArtist(artistRepository.find(c.getArtist()));
                    dto.setLocatie(locatieRepository.find(c.getLocatie()));
                    return dto;
                })
                .toList();
    }

    @Override
    public void sellTickets(ConcertDto concert, int nrBilete) throws FestivalException {
        Concert c = concertRepository.find(concert.getcId());
        if(c == null) {
            throw new FestivalException("Invalid concert");
        }
        if(c.getDisponibile() < nrBilete) {
            throw new FestivalException("Disponibile less than number of biletes");
        }
        c.setDisponibile(c.getDisponibile() - nrBilete);
        c.setOcupate(c.getOcupate() + nrBilete);
        concert.setDisponibile(c.getDisponibile());
        concert.setOcupate(c.getOcupate());
        try{
            concertRepository.update(c);
        } catch(Exception e){
            throw new FestivalException("Something went wrong");
        }
        logger.info("Concert care va fi notificat: " + concert.toString() + "id=" + concert.getcId());
        notifyVanzare(concert);

    }

    private final int defaultThreadsNo=3;
    private void notifyVanzare(ConcertDto c) throws FestivalException {

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var user : loggedClients.values()){
                executor.execute(() -> {
                    try {
                        user.updateBilete(c);
                    } catch (FestivalException e) {
                        logger.error("Error updating concert {} {}", c.getcId(), e);
                    }
                });
        }

        executor.shutdown();
    }
}
