package generators;

import cinema.sessions.*;
import enums.Month;

import java.util.*;

public class MovieSessionGenerator {

    private MovieSessionGenerator() {}

    public static Map<MovieSession, Movie> generateMovieSessionMap(int count) {
        Movie[] movies = {
                new Movie("Deadpool"),
                new Movie("Avengers"),
                new Movie("Batman"),
                new Movie("Harry Potter"),
                new Movie("Star Wars"),
                new Movie("Gladiator"),
                new Movie("Dead Poets Society"),
                new Movie("Pulp Fiction"),
                new Movie("Terminator"),
                new Movie("Kill Bill 2"),
        };

        Map<MovieSession, Movie> map = new HashMap<>();

        for (int i = 0; i < count; i++) {
            int date = (int) (Math.random() * (31 - 1)) + 1;
            int time = (int) (Math.random() * (23 - 12)) + 12;
            int randMovieIndex = (int) (Math.random() * movies.length);
            int randMonthIndex = (int) (Math.random() * Month.values().length);

            map.put(new MovieSession(Month.values()[randMonthIndex].name() +
                    " " + date, time + ":00"), movies[randMovieIndex]);
        }

        return map;
    }
}
