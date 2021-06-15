package com.hubvisory.movizz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("tmdb.api")
@Configuration
@ComponentScan("com.hubvisory.movizz")
public class ApplicationProperties {

    private String url;

    private String key;

    private String get_movie_from_id;

    private String get_person;

    private String discover_movie;

    private String get_credits_from_movie;

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getGet_movie_from_id() {
        return get_movie_from_id;
    }

    public String getGet_person() {
        return get_person;
    }

    public String getDiscover_movie() {
        return discover_movie;
    }

    public String getGet_credits_from_movie() {
        return get_credits_from_movie;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setGet_movie_from_id(String get_movie_from_id) {
        this.get_movie_from_id = get_movie_from_id;
    }

    public void setGet_person(String get_person) {
        this.get_person = get_person;
    }

    public void setDiscover_movie(String discover_movie) {
        this.discover_movie = discover_movie;
    }

    public void setGet_credits_from_movie(String get_credits_from_movie) {
        this.get_credits_from_movie = get_credits_from_movie;
    }
}
