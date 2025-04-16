
using log4net;
using Microsoft.Extensions.DependencyInjection;
using Repo;
using Services;

using Networking.utils;

namespace Server
{
    public class Program
    {
        private static int DEFAULT_PORT = 55556;
        private static String DEFAULT_IP = "127.0.0.1";
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));
        public static void Main(string[] args)
        {
            log4net.Config.XmlConfigurator.Configure();

            var services = new ServiceCollection();
            services.AddMyServer();
            var provider = services.BuildServiceProvider();

            var festivalService = provider.GetRequiredService<IFestivalService>();

            log.Info("Starting chat server");
            log.Info("Reading properties from app.config ...");
            int port = DEFAULT_PORT;
            String ip = DEFAULT_IP;
            int portS = ServerSettings.GetPort();
            if (portS == null)
            {
                log.Debug("Port property not set. Using default value " + DEFAULT_PORT);
            }
            else
            {
                port = portS;
            }
                String ipS = ServerSettings.GetIp();

            if (ipS == null)
            {
                log.Info("IP property not set. Using default value " + DEFAULT_IP);
            }

            log.DebugFormat("Starting server on IP {0} and port {1}", ip, port);

            JsonServer server = new JsonServer(ip, port, festivalService);
            
            server.Start();
            log.Debug("Server started ...");

        }
    }
}

