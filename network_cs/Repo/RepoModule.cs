using Microsoft.Extensions.DependencyInjection;
using Repo.database;


namespace Repo
{
    public static class RepoModule
    {
        public static IServiceCollection AddMyRepos(this IServiceCollection services)
        {

            // Get the connection string from appsettings.json
            var connectionString = RepoConfig.GetConString();
            FestivalContext contenxt = new FestivalContext(connectionString);
         
            services.AddScoped<IAngajatRepo>(sp =>
            {
                return new AngajatDBRepo(contenxt);
            });
            services.AddScoped<IArtistRepo>(sp =>
            {
                return new ArtistDBRepo(contenxt);
            });
            services.AddScoped<ILocatieRepo>(sp =>
            {
                return new LocatieDBRepo(contenxt);
            });
            services.AddScoped<IConcertRepo>(sp =>
            {
                return new ConcertDBRepo(contenxt);
            });

            return services;
        }
    }
}
