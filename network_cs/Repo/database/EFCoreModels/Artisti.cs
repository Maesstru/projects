using System;
using System.Collections.Generic;

namespace Repo.database.EFCoreModels;

public partial class Artisti:Entity<int>
{

    public string? Nume { get; set; }

    public virtual ICollection<Concerte> Concertes { get; set; } = new List<Concerte>();
}
