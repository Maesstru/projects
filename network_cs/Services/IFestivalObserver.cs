using Dtos;

namespace Services
{
    public interface IFestivalObserver
    {
        void UpdateConcert(ConcertDto c);
    }
}
