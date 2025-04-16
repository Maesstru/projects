package models;

public class Artist extends Entity<Long> {
    private String name;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
