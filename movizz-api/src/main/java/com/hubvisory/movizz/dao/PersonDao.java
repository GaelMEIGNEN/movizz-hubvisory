package com.hubvisory.movizz.dao;

import com.hubvisory.movizz.bean.Person;

import java.util.List;

public interface PersonDao {
    Person[] getActorsFromMovieTMDBAPI(List<String> criterias, String url, String uri, String key);
}
