using log4net;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Dtos;
using System.Runtime.CompilerServices;

namespace Networking.jsonprotocol
{
    internal class FestivalClientWorker : IFestivalObserver
    {
        private IFestivalService server;
        private TcpClient connection;

        private NetworkStream stream;
        private volatile bool connected;
        private static readonly ILog log = LogManager.GetLogger(typeof(FestivalClientWorker));
        public FestivalClientWorker(IFestivalService server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {

                stream = connection.GetStream();
                connected = true;
            }
            catch (Exception e)
            {
                log.Error(e.StackTrace);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            try
            {
                switch (request.type)
                {
                    case RequestType.LOGIN:
                        return handleLogin(request);
                    case RequestType.GET_ALL_CONCERTS:
                        return handleGetAllConcerts(request);
                    case RequestType.SELL_TICKETS:
                        return handleSellTickets(request);
                    case RequestType.LOGOUT:
                        return handleLogout(request);
                }
            }
            catch (Exception e)
            {
                log.Error(e.StackTrace);
            }
            return response;
        }

        private Response handleGetAllConcerts(Request request)
        {
            Response response = null;
            try
            {
                var concerts = server.GetConcerts();
                response = new Response.Builder()
                    .Type(ResponseType.GET_CONCERTE)
                    .Concerts(concerts)
                    .Build();
            }
            catch (Exception e)
            {
                response = new Response.Builder()
                    .Type(ResponseType.ERROR)
                    .Message(e.Message)
                    .Build();
            }
            return response;
        }

        private Response handleLogin(Request request)
        {
            Response response = null;
            try
            {
                server.Login(request.angajat, this);
                response = new Response.Builder()
                    .Type(ResponseType.OK)
                    .Message("Login successful")
                    .Build();
            }
            catch (Exception e)
            {
                response = new Response.Builder()
                    .Type(ResponseType.ERROR)
                    .Message(e.Message)
                    .Build();
            }
            return response;
        }

        private Response handleSellTickets(Request request)
        {
            Response response = null;
            try
            {
                int nrTickets = request.nrTickets ?? 0;
                server.SellTickets(request.concert, nrTickets);
                response = new Response.Builder()
                    .Type(ResponseType.OK)
                    .Message("Tickets sold successfully")
                    .Build();
            }
            catch (Exception e)
            {
                response = new Response.Builder()
                    .Type(ResponseType.ERROR)
                    .Message(e.Message)
                    .Build();
            }
            return response;
        }

        private Response handleLogout(Request request)
        {
            Response response = null;
            try
            {
                server.Logout(request.angajat);
                connected = false;
                response = new Response.Builder()
                    .Type(ResponseType.OK)
                    .Message("Logout successful")
                    .Build();
            }
            catch (Exception e)
            {
                log.Error(e.StackTrace);
                response = new Response.Builder()
                    .Type(ResponseType.ERROR)
                    .Message(e.Message)
                    .Build();
            }
            return response;
        }
        public virtual void run()
        {
            using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
            while (connected)
            {
                try
                {
                    string requestJson = reader.ReadLine(); // Read JSON line-by-line
                    if (string.IsNullOrEmpty(requestJson)) continue;
                    log.DebugFormat("Received json request {0}", requestJson);
                    Request request = JsonSerializer.Deserialize<Request>(requestJson);
                    log.DebugFormat("Deserializaed Request {0} ", request);
                    Response response = handleRequest(request);
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    log.ErrorFormat("run eroare {0}", e.Message);
                    if (e.InnerException != null)
                        log.ErrorFormat("run inner error {0}", e.InnerException.Message);
                    log.Error(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    log.Error(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                log.Error("Error " + e);
            }
        }

        private void sendResponse(Response response)
        {
            //de modificat pentru json
            String jsonString = JsonSerializer.Serialize(response);
            log.DebugFormat("sending response {0}", jsonString);
            lock (stream)
            {
                byte[] data = Encoding.UTF8.GetBytes(jsonString + "\n"); // Append newline 
                stream.Write(data, 0, data.Length);
                stream.Flush();
            }

        }

        public void UpdateConcert(ConcertDto c)
        {
            Response response = new Response.Builder()
                .Type(ResponseType.UPDATE_CONCERT)
                .Concert(c)
                .Build();
            try
            {
                sendResponse(response);
            }
            catch (Exception e)
            {
                log.ErrorFormat("Error sending update {0}", e.Message);
                if (e.InnerException != null)
                    log.ErrorFormat("Error sending update inner {0}", e.InnerException.Message);
            }
        }
    }
}
