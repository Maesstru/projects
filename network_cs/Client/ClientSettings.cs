using Networking.jsonprotocol;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client
{
    internal static class ClientSettings
    {
        public static IFestivalService GetProxy()
        {
            return new FestivalServerProxy("127.0.0.1", 55559);
        }
    }
}
