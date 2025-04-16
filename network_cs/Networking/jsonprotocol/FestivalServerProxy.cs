using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using log4net;
using Services;
using Dtos;
using System.Collections.ObjectModel;
using System.Text.Json;


namespace Networking.jsonprotocol
{
    public class FestivalServerProxy : IFestivalService
    {
        private string host;
        private int port;

        private IFestivalObserver client;
        private NetworkStream stream;
        private TcpClient connection;
        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        private static readonly ILog log = LogManager.GetLogger(typeof(FestivalServerProxy));
        public FestivalServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();

                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }
        public void Login(AngajatDto angajat, IFestivalObserver client)
        {
            initializeConnection();
            Request req = new Request.Builder()
                .Type(RequestType.LOGIN)
                .Angajat(angajat)
                .Build();

            sendRequest(req);
            Response rsp = readResponse();
            if (rsp.type == ResponseType.ERROR)
            {
                throw new FestivalException(rsp.message);
            }
            if (rsp.type == ResponseType.OK)
            {
                this.client = client;
                log.Info("User " + angajat.Username + " logged in");
                return;
            }
        }
        public void Logout(AngajatDto angajat)
        {
            Request req = new Request.Builder()
                .Type(RequestType.LOGOUT)
                .Angajat(angajat)
                .Build();
            sendRequest(req);
            Response rsp = readResponse();
            if (rsp.type == ResponseType.ERROR)
            {
                throw new FestivalException(rsp.message);
            }
            if (rsp.type == ResponseType.OK)
            {
                log.Info("User " + angajat.Username + " logged out");
                finished = true;
                connection.Close();
                return;
            }
        }
        public Collection<ConcertDto> GetConcerts()
        {
            Request request = new Request.Builder()
                .Type(RequestType.GET_ALL_CONCERTS)
                .Build();

            sendRequest(request);
            Response response = readResponse();
            if (response.type == ResponseType.ERROR)
            {
                throw new FestivalException(response.message);
            }
            if (response.type == ResponseType.GET_CONCERTE)
            {
                log.Info("Concerts received");
                return new Collection<ConcertDto>(response.concerts);
            }
            throw new FestivalException("Unknown response " + response);
        }

        public void SellTickets(ConcertDto concert, int nrTickets)
        {
            Request request = new Request.Builder()
                .Type(RequestType.SELL_TICKETS)
                .Concert(concert)
                .NrTickets(nrTickets)
                .Build();

            sendRequest(request);
            Response response = readResponse();
            if (response.type == ResponseType.ERROR)
            {
                throw new FestivalException(response.message);
            }
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        private void handleUpdate(Response response)
        {
            log.DebugFormat("handleUpdate called with {0}", response);
            if (response.type == ResponseType.UPDATE_CONCERT)
            {
                try
                {
                    client.UpdateConcert(response.concert);
                }
                catch (Exception e)
                {
                    log.Error("Error in handleUpdate " + e);

                }
            }
        }

        private bool isUpdate(Response response)
        {
            return response.type == ResponseType.UPDATE_CONCERT;
        }

        public virtual void run()
        {
            using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
            while (!finished)
            {
                try
                {
                    string responseJson = reader.ReadLine();
                    if (string.IsNullOrEmpty(responseJson))
                        continue; //nu s-a citit un raspuns valid
                    Response response = JsonSerializer.Deserialize<Response>(responseJson);
                    log.Debug("response received " + response);
                    if (isUpdate(response))
                    {
                        handleUpdate(response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue(response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    log.Error("Reading error " + e);
                }

            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                lock (stream)
                {
                    
                    string jsonRequest = JsonSerializer.Serialize(request);
                    log.DebugFormat("Sending request {0}", jsonRequest);
                    byte[] data = Encoding.UTF8.GetBytes(jsonRequest + "\n"); // Append newline 
                    stream.Write(data, 0, data.Length);
                    stream.Flush();

                }
            }
            catch (Exception e)
            {
                throw new FestivalException("Error sending object " + e);
            }

        }

        
    }
}
