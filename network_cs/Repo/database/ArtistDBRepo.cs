using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repo.database.EFCoreModels;
using Repo.memory;




namespace Repo.database
{
    public class ArtistDBRepo : MemoryRepo<int,Artisti>,IArtistRepo
    {
        FestivalContext _context;

        public ArtistDBRepo(FestivalContext context)
        {
            _context = context;
            load();
        }

        private void load()
        {
            entities.Clear();
            foreach (var item in _context.Artistis)
            {
                entities[item.Id] = item;
            }
        }
    }
}
