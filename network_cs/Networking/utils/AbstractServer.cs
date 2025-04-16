using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using log4net;

namespace Networking.utils
{
    public abstract class AbstractServer
    {
        private TcpListener server;
        private String host;
        private int port;

        private static readonly ILog log = LogManager.GetLogger(typeof(AbstractServer));
        public AbstractServer(String host, int port)
        {
            this.host = host;
            this.port = port;
        }
        public void Start()
        {
            IPAddress adr = IPAddress.Parse(host);
            IPEndPoint ep = new IPEndPoint(adr, port);
            server = new TcpListener(ep);
            server.Start();
            while (true)
            {
                log.Debug("Waiting for clients ...");
                TcpClient client = server.AcceptTcpClient();
                log.Debug("Client connected ...");
                processRequest(client);
            }
        }

        public abstract void processRequest(TcpClient client);

    }
}
