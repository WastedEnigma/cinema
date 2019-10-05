package cinema;

import cinema.people.*;
import cinema.structure.*;

import java.util.*;

public class Cinema {

    private String name;
    private List<CinemaHall> cinemaHalls;
    private Set<Visitor> visitors = new HashSet<>();

    public Cinema(String name, List<CinemaHall> cinemaHalls) {
        this.name = name;
        this.cinemaHalls = cinemaHalls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHalls;
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", cinemaHalls=" + cinemaHalls +
                '}';
    }
}
