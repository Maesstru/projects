using System;
using System.Collections.Generic;

namespace Repo.database.EFCoreModels;

public partial class Concerte:Entity<int>
{
    public long Data { get; set; }

    public int Locatie { get; set; }

    public int LocuriDisponibile { get; set; }

    public int Artist { get; set; }

    public int LocuriOcupate { get; set; }

    public virtual Artisti ArtistNavigation { get; set; } = null!;

    public virtual Locatii LocatieNavigation { get; set; } = null!;
}
