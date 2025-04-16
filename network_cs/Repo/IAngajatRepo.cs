using Repo.database.EFCoreModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repo
{
    public interface IAngajatRepo : IRepo<int,Angajati>
    {
        Angajati? FindByCredentials(string username, string password);
    }
}
