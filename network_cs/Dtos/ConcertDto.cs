using Repo.database.EFCoreModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dtos
{
    public class ConcertDto
    {
        public int Id { get; set; }
        public DateTime date {  get; set; }
        public LocatieDto locatie {  get; set; }
        public int Disponibile {  get; set; }
        public int Ocupate {  get; set; }
        public ArtistDto artist { get; set; }

        public ConcertDto() { }

        public ConcertDto(Concerte c)
        {
            Id = c.Id;
            date = DateTimeOffset.FromUnixTimeMilliseconds(c.Data).DateTime;
            locatie = new LocatieDto(c.LocatieNavigation);
            artist = new ArtistDto(c.ArtistNavigation);
            Disponibile = c.LocuriDisponibile;
            Ocupate = c.LocuriOcupate;
        }

        public override string? ToString()
        {
            return $"{artist.Name}: {locatie.Oras} || Data: {date.Day}/{date.Month}/{date.Year} || Ora: {date.Hour}:{date.Minute}";
        }
    }
}
