package com.nabenik.repository;


import com.nabenik.model.Movie;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestScoped
@Default
public class MovieRepository {

    @Inject
    EntityManager em;

    public Movie findById(@NotNull Long id){
        return em.find(Movie.class, id);
    }

    public void create(Movie movie){
            em.persist(movie);
    }

    public Movie update(@Valid Movie movie){
        em.merge(movie);
        return movie;
    }

    public void delete(@Valid Movie movie){
        em.remove(movie);
    }

    public List<Movie> listAll(@NotNull String title){

        String query = "SELECT m FROM Movie m " +
                "where m.title LIKE :title";

        Query typedQuery = em.createQuery(query)
                .setParameter("title", "%".concat(title).concat("%"));

        return typedQuery.getResultList();
    }
}
