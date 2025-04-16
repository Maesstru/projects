using log4net;
using Networking.jsonprotocol;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Networking.utils
{
    public class JsonServer : ConcurrentServer
    {
        private IFestivalService server;
        private FestivalClientWorker worker;
        private static readonly ILog log = LogManager.GetLogger(typeof(JsonServer));
        public JsonServer(string host, int port, IFestivalService server) : base(host, port)
        {
            this.server = server;
            log.Debug("Creating JsonChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new FestivalClientWorker(server, client);
            return new Thread(worker.run);
        }
    }
}
