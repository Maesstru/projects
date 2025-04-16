using System;
using System.Collections.Generic;

namespace Repo.database.EFCoreModels;

public partial class Angajati: Entity<int>
{
    public string? Username { get; set; }

    public string? Parola { get; set; }
}
