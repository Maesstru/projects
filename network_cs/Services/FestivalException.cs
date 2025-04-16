using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class FestivalException : Exception
    {
        public FestivalException() : base() { }

        public FestivalException(String msg) : base(msg) { }

        public FestivalException(String msg, Exception ex) : base(msg, ex) { }
    }
}
