using Dtos;
using System.Collections.ObjectModel;


namespace Services
{
    public interface IFestivalService
    {
        void Login(AngajatDto angajat,IFestivalObserver client);
        void Logout(AngajatDto angajat);
        Collection<ConcertDto> GetConcerts();
        void SellTickets(ConcertDto concert,int nrTickets);
    }
}
