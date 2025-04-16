using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Dtos;

namespace Networking.jsonprotocol
{
    public class Response
    {
        public ResponseType? type { get; set; }
        public string? message { get; set; }
        public Collection<ConcertDto>? concerts { get; set; }
        public ConcertDto? concert { get; set; }
        public Response() { }
        public class Builder
        {
            public Builder() { }
            private Response response = new Response();
            public Builder Type(ResponseType type)
            {
                response.type = type;
                return this;
            }
            public Builder Message(string message)
            {
                response.message = message;
                return this;
            }
            public Builder Concert(ConcertDto concert)
            {
                response.concert = concert;
                return this;
            }
            public Builder Concerts(Collection<ConcertDto> concerts)
            {
                response.concerts = concerts;
                return this;
            }
            public Response Build()
            {
                return response;
            }
        }
    }
}
