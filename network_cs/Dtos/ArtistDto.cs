using Repo.database.EFCoreModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dtos
{
    public class ArtistDto
    {
        public int Id { get; set; }
        public string? Name { get; set; }

        public ArtistDto() { }

        public ArtistDto(int id, string name)
        {
            Id = id;
            Name = name;
        }

        public ArtistDto(Artisti a)
        {
            Id = a.Id;
            Name = a.Nume;
        }

        public override string? ToString()
        {
            return Name;
        }
    }
}
