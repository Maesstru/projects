package models;

public class Angajat extends Entity<Long>{
    private String username;
    private String password;

    public Angajat(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Angajat(Long id, String username, String password) {
        setId(id);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
