using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repo
{
    public interface IRepo<ID,E> where E : Entity<ID>
    {
        ICollection<E> GetAll();
        E? Update(E e);
    }
}
