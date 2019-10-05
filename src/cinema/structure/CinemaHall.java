package cinema.structure;

import cinema.sessions.*;

import java.util.*;

public class CinemaHall {

    private int number;
    private Map<MovieSession, Movie> sessionMovieMap;

    public CinemaHall(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Map<MovieSession, Movie> getSessionMovieMap() {
        return sessionMovieMap;
    }

    public void setSessionMovieMap(Map<MovieSession, Movie> sessionMovieMap) {
        this.sessionMovieMap = sessionMovieMap;
    }

    @Override
    public String toString() {
        return "CinemaHall{" +
                "number=" + number +
                ", sessionMovieMap=" + sessionMovieMap +
                '}';
    }
}
