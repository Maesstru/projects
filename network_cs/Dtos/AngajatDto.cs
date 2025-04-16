using Repo.database.EFCoreModels;

namespace Dtos
{
    public class AngajatDto
    {
        public int? Id { get; set; }
        public string? Username {  get; set; }
        public string? Password { get; set; }

        public AngajatDto() { }
        public AngajatDto(int id, string username, string password)
        {
            Id = id;
            Username = username;
            Password = password;
        }

        public AngajatDto(string username, string password)
        {
            Username = username;
            Password = password;
        }

        public AngajatDto(Angajati a)
        {
            Id = a.Id;
            Username = a.Username;
            Password = a.Parola;
        }
    }
}
