package com.hubvisory.movizz.beans;

import java.util.List;

public class Actor {

    private long Id;

    private String firstName;

    private String lastName;

    private String urlPicture;

    private List<Movie> starsInMovies;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public List<Movie> getStarsInMovies() {
        return starsInMovies;
    }

    public void setStarsInMovies(List<Movie> starsInMovies) {
        this.starsInMovies = starsInMovies;
    }
}
