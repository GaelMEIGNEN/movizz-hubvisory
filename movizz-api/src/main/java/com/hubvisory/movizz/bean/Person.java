package com.hubvisory.movizz.bean;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private boolean adult;

    private int gender;

    private Long id;

    private String known_for_department;

    private String name;

    private String original_name;

    private double popularity;

    private String profile_path;

    public Person() {
    }

    public Person(boolean adult, int gender, Long id, String known_for_department, String name, String original_name, double popularity, String profile_path, int cast_id, String character, String credit_id, int order) {
        this.adult = adult;
        this.gender = gender;
        this.id = id;
        this.known_for_department = known_for_department;
        this.name = name;
        this.original_name = original_name;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        if (profile_path != null) {
            if (!profile_path.startsWith("https://image.tmdb.org")) {
                this.profile_path = "https://image.tmdb.org/t/p/w200" + profile_path;
            } else {
                this.profile_path = profile_path;
            }
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "adult=" + adult +
                ", gender=" + gender +
                ", id=" + id +
                ", known_for_department='" + known_for_department + '\'' +
                ", name='" + name + '\'' +
                ", original_name='" + original_name + '\'' +
                ", popularity=" + popularity +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }

    /**
     * Takes all actors from an array of actor and put them in a list of actor
     *
     * @param actors An array of actor
     * @return A list of actor
     */
    public static List<Person> fromActorTableToActorList (Person[] actors) {
        List<Person> actorsList = new ArrayList<>();
        if (actors != null) {
            for (Person actor:
                    actors) {
                actorsList.add(actor);
            }
        }
        return actorsList;
    }

    /**
     * Finds if the actor in parameter stars in the movie in parameter
     *
     * @param actor An actor object
     * @param movie A movie object
     * @return "YES" if the actor stars in the movie, "NO" otherwise
     */
    public static String isActorStarringInMovie(Person actor, Movie movie) {
        if (actor != null && movie != null) {
            if (movie.getActors_in_movie() != null) {
                if (movie.getActors_in_movie().contains(actor)) {
                    return "YES";
                } else {
                    return "NO";
                }
            }
        }
        return "";
    }
}
