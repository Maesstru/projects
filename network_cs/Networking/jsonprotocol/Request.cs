using Dtos;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.jsonprotocol
{
    public class Request
    {
        public RequestType? type { get; set; }
        public ConcertDto? concert { get; set; }
        public int? nrTickets { get; set; }
        public AngajatDto? angajat { get; set; }

        public Request() { }

        public class Builder
        {
            public Builder() { }
            private Request request = new Request();
            public Builder Type(RequestType type)
            {
                request.type = type;
                return this;
            }
            public Builder Concert(ConcertDto concert)
            {
                request.concert = concert;
                return this;
            }
            public Builder NrTickets(int nrTickets)
            {
                request.nrTickets = nrTickets;
                return this;
            }
            public Builder Angajat(AngajatDto angajat)
            {
                request.angajat = angajat;
                return this;
            }
            public Request Build()
            {
                return request;
            }
        }
    }
}
