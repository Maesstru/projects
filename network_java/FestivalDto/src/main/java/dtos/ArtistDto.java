package dtos;

import models.Artist;

import java.io.Serializable;

public class ArtistDto implements Serializable {
    private Long id;
    private String name;

    public ArtistDto() {}

    public ArtistDto(Artist artist) {
        id = artist.getId();
        name = artist.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
