package com.nabenik.repository;

import com.nabenik.model.Actor;
import com.nabenik.model.Movie;
import com.nabenik.rest.JAXRSConfiguration;
import com.nabenik.util.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MovieRepositoryTest {

    @Inject
    MovieRepository movieRepository;

    @Deployment
    public static WebArchive createDeployment(){
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClass(Movie.class)
                .addClass(Actor.class)
                .addClass(EntityManagerProducer.class)
                .addClass(MovieRepository.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/persistence.xml");

        System.out.println(war.toString(true));

        return war;
    }

    @Test
    public void create() {
        Movie movie = new Movie("El silencio de Jimmy", "2014", "4 años");
        movieRepository.create(movie);


        System.out.println("Movie Id " + movie.getMovieId());

        assertNotNull(movie.getMovieId());
    }

    @Test
    public void find() {

        Movie movie = new Movie("El silencio de Jimmy", "2014", "4 años");
        movieRepository.create(movie);
        Movie found = movieRepository.findById(movie.getMovieId());
        assertNotNull(found);
    }

    @Test
    public void update() {
        Movie movie = new Movie("El silencio de Jimmy", "2014", "4 años");
        movieRepository.create(movie);

        Movie update = new Movie("El silencio de Jimmy 2, Electric boogaloo", "2016", "4 años mas");
        update.setMovieId(movie.getMovieId());
        movieRepository.update(update);

        Movie modified = movieRepository.findById(update.getMovieId());
        assertSame("2016",modified.getYear());
        assertSame("4 años mas",modified.getDuration());
        assertSame("El silencio de Jimmy 2, Electric boogaloo",modified.getTitle());
    }

    @Test
    public void delete() {

        Movie movie = new Movie("El silencio de Jimmy", "2014", "4 años");
        movieRepository.create(movie);

        movieRepository.delete(movie);

        Movie found = movieRepository.findById(1L);
        assertNull(found);
    }

}
