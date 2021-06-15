package com.hubvisory.movizz.dao;

import com.hubvisory.movizz.bean.Movie;

import java.util.List;

public interface MovieDao {
    Movie[] getMoviesFromTMDBAPI(List<String> criterias, String url, String uri, String key);
}
