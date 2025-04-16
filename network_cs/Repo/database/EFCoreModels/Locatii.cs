using System;
using System.Collections.Generic;

namespace Repo.database.EFCoreModels;

public partial class Locatii:Entity<int>
{
    public string? Oras { get; set; }

    public string? Strada { get; set; }

    public string? Numar { get; set; }

    public virtual ICollection<Concerte> Concertes { get; set; } = new List<Concerte>();
}
