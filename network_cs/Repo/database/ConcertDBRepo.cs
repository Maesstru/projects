using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repo.database.EFCoreModels;
using Repo.memory;

namespace Repo.database
{
    public class ConcertDBRepo : MemoryRepo<int,Concerte>, IConcertRepo
    {
        private FestivalContext _context;
        public ConcertDBRepo(FestivalContext context)
        {
            _context = context;
            load();
        }

        public Concerte? Find(int id)
        {
            return entities.GetValueOrDefault(id);
        }

        public Concerte? Update(Concerte concerte)
        {
            Concerte? ret = base.Update(concerte);
            _context.Update(concerte);
            _context.SaveChanges();
            return ret;
        }

        private void load()
        {
            entities.Clear();
            foreach (var item in _context.Concertes)
            {
                entities[item.Id] = item;
            }
        }
    }
}
