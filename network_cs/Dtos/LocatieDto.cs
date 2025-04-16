using Repo.database.EFCoreModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dtos
{
    public class LocatieDto
    {
        public string Oras {  get; set; }
        public string Strada {  get; set; }
        public string Numar { get; set; }

        public LocatieDto() { }

        public LocatieDto(Locatii l)
        {
            Oras = l.Oras;
            Strada = l.Strada;
            Numar = l.Numar;
        }

        public override string? ToString()
        {
            return $"{Oras}";
        }
    }
}
