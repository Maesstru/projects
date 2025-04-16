using Dtos;
using Repo;
using Repo.database.EFCoreModels;
using Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;

namespace Server.Service
{
    public class ServiceImp : IFestivalService
    {
        IAngajatRepo angajatRepo;
        IArtistRepo artistRepo;
        IConcertRepo concertRepo;
        ILocatieRepo locatieRepo;

        Dictionary<string, IFestivalObserver> loggedClients = new Dictionary<string, IFestivalObserver>();
        private static readonly ILog log = LogManager.GetLogger(typeof(ServiceImp));
        public ServiceImp() { }
        public ServiceImp(IAngajatRepo angajatRepo, IArtistRepo artistRepo, IConcertRepo concertRepo, ILocatieRepo locatieRepo)
        {
            this.angajatRepo = angajatRepo;
            this.artistRepo = artistRepo;
            this.concertRepo = concertRepo;
            this.locatieRepo = locatieRepo;

        }

        public Collection<ConcertDto> GetConcerts()
        {
            Collection<ConcertDto> concertDtos = new Collection<ConcertDto>();
            foreach (var item in concertRepo.GetAll())
            {
                concertDtos.Add(new ConcertDto(item));
            }

            return concertDtos;
        }

        public void Login(AngajatDto angajat, IFestivalObserver client)
        {
            Angajati? found = angajatRepo.FindByCredentials(angajat.Username, angajat.Password);



            if (found == null)
                throw new FestivalException("Invalid username or password");
            if (loggedClients.ContainsKey(angajat.Username))
                throw new FestivalException("User already logged in!");
            loggedClients[angajat.Username] = client;
            log.Info("User " + angajat.Username + " logged in");

        }

        public void Logout(AngajatDto angajat)
        {
            if(loggedClients.Remove(angajat.Username) == false)
            {
                throw new FestivalException("Server couldn't log out user");
            }
            log.Info("User " + angajat.Username + " logged out");
        }

        public void SellTickets(ConcertDto concert, int nrTickets)
        {
            Concerte? c = concertRepo.Find(concert.Id);
            if (c == null)
                throw new FestivalException("Concert not found!");
            if (nrTickets > c.LocuriDisponibile)
                throw new FestivalException("Not enough tickets remaining");

            c.LocuriDisponibile -= nrTickets;
            c.LocuriOcupate += nrTickets;
            concert.Disponibile = c.LocuriDisponibile;
            concert.Ocupate = c.LocuriOcupate;

            try
            {
                concertRepo.Update(c);
            }
            catch (Exception e)
            {
                throw new FestivalException("Error while selling tickets" + e.Message);
            }
            log.Info("Sold " + nrTickets + " tickets for concert " + concert.Id);
            NotifyClients(concert);
        }

        private void NotifyClients(ConcertDto concert)
        {
            foreach (var client in loggedClients.Values)
            {
                try
                {
                    Task.Run(() => client.UpdateConcert(concert));
                }
                catch (Exception e)
                {
                    log.Error("Error notifying client: " + e.Message);
                }
            }
        }
    }
}
