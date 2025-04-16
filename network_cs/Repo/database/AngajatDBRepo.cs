using Repo.database.EFCoreModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repo.memory;

namespace Repo.database
{
    public class AngajatDBRepo :  MemoryRepo<int,Angajati>,IAngajatRepo 
    {
        private FestivalContext _context;
        public AngajatDBRepo(FestivalContext context) 
        {
            _context = context;
            load();
        }

        public Angajati? FindByCredentials(string username, string password)
        {
            foreach (var item in entities.Values)
            {
                if(item.Username == username && item.Parola == password)
                    return item;
            }
            return null;
        }

        private void load()
        {
            entities.Clear();
            foreach (var item in _context.Angajatis)
            {
                entities[item.Id] = item;
            }
        }
    }
}
