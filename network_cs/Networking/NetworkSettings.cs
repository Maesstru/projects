using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    public static class NetworkSettings
    {
        static int port=55559;
        static string host="localhost";
        static string ip="127.0.0.1";

        public static int GetPort()
        {
            return port;
        }

        public static string GetIp()
        {
            return ip;
        }

        public static string GetHost()
        {
            return host;
        }
    }
}
