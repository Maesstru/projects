using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repo.database.EFCoreModels;
using Repo.memory;


namespace Repo.database
{
    public class LocatieDBRepo: MemoryRepo<int, Locatii>, ILocatieRepo
    {
        private FestivalContext _context;

        public LocatieDBRepo(FestivalContext context)
        {
            _context = context;
            load();
        }
        private void load()
        {
            entities.Clear();
            foreach (var item in _context.Locatiis)
            {
                entities[item.Id] = item;
            }
        }
    }
}
