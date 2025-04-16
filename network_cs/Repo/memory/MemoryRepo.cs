using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Repo;

namespace Repo.memory
{
    public class MemoryRepo<ID, E> : IRepo<ID, E> where E : Entity<ID> where ID : notnull
    {
        internal Dictionary<ID, E> entities = new Dictionary<ID, E>();
        public ICollection<E> GetAll()
        {
            return entities.Values;
        }

        public E? Update(E e)
        {
            if(entities.ContainsKey(e.Id))
            {
                entities[e.Id] = e;
                return null;
            }
            return e;
        }
    }
}
