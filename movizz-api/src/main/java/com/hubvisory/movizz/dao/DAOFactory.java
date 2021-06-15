package com.hubvisory.movizz.dao;

import com.hubvisory.movizz.dao.impl.MovieDaoImpl;
import com.hubvisory.movizz.dao.impl.PersonDaoImpl;
import org.springframework.stereotype.Component;

@Component
public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }

        return instance;
    }

    public MovieDao getMovieDao() {
        return new MovieDaoImpl(this);
    }

    public PersonDao getPersonDao() {
        return new PersonDaoImpl(this);
    }
}
