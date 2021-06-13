package com.hubvisory.movizz.bean;

import java.time.LocalDate;
import java.util.List;

public class Movie {


    private Boolean adult;

    private String backdrop_path;

    private List<Integer> genre_ids;

    private long id;

    private String original_language;

    private String original_title;

    private String overview;

    private double popularity;

    private String poster_path;

    private LocalDate release_date;

    private String title;

    private boolean video;

    private double vote_average;

    private int vote_count;

    private List<Person> actors_in_movie;

    public Movie() {
    }

    public Movie(Boolean adult, String backdrop_path, List<Integer> genre_ids, long id, String original_language, String originial_title, String overview, double popularity, String poster_path, LocalDate release_date, String title, boolean video, double vote_average, int vote_count, List<Person> actors_in_movie) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_language = original_language;
        this.original_title = originial_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.actors_in_movie = actors_in_movie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        if (poster_path != null) {
            if (!poster_path.startsWith("https://image.tmdb.org")) {
                this.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            } else {
                this.poster_path = poster_path;
            }
        }
    }

    public List<Person> getActors_in_movie() {
        return actors_in_movie;
    }

    public void setActors_in_movie(List<Person> actors_in_movie) {
        this.actors_in_movie = actors_in_movie;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genre_ids=" + genre_ids +
                ", id=" + id +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date=" + release_date +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", actors_in_movie=" + actors_in_movie +
                '}';
    }
}
