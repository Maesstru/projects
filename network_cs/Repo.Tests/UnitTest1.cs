using Repo.database;
using Repo;
using Microsoft.Extensions.DependencyInjection;

namespace Repo.Tests
{
    public class UnitTest1
    {
        [Fact]
        public void Test1()
        {
            var services = new ServiceCollection();
            services.AddMyRepos();
            var provider = services.BuildServiceProvider();

            var angajatRepo = provider.GetRequiredService<IAngajatRepo>();

            Assert.NotEmpty(angajatRepo.GetAll());
        }
    }
}
