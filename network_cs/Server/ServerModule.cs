using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Repo;
using Server.Service;
using Services;

namespace Server
{
    public static class ServerModule
    {
        public static IServiceCollection AddMyServer(this IServiceCollection services)
        {
            // Get the connection string from appsettings.json
            services.AddMyRepos();
            IAngajatRepo angajatRepo = services.BuildServiceProvider().GetRequiredService<IAngajatRepo>();
            IArtistRepo artistRepo = services.BuildServiceProvider().GetRequiredService<IArtistRepo>();
            IConcertRepo concertRepo = services.BuildServiceProvider().GetRequiredService<IConcertRepo>();
            ILocatieRepo locatieRepo = services.BuildServiceProvider().GetRequiredService<ILocatieRepo>();

            services.AddScoped<IFestivalService>(sp =>
            {
                return new ServiceImp(angajatRepo,artistRepo,concertRepo,locatieRepo);
            });

            return services;
        }
    }
}
