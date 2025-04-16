﻿using Repo.database.EFCoreModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repo
{
    public interface IConcertRepo : IRepo<int,Concerte>
    {
        Concerte? Find(int id);
    }
}
