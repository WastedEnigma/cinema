package cinema.people;

import cinema.Cinema;
import comparators.MovieNameComparator;
import cinema.sessions.*;
import cinema.structure.CinemaHall;

import java.util.*;

public class Visitor {

    private String firstName;
    private String lastName;
    private double balance;
    private boolean hasTicket;
    private Map<MovieSession, Movie> sessionMovieMap = new HashMap<>();

    public Visitor(String firstName, String lastName, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean hasTicket() {
        return hasTicket;
    }

    public void setHasTicket(boolean hasTicket) {
        this.hasTicket = hasTicket;
    }

    public Map<MovieSession, Movie> getSessionMovieMap() {
        return sessionMovieMap;
    }

    public void lookupAllCinemas(List<Cinema> cinemas) {
        System.out.println();

        if (!cinemas.isEmpty()) {
            for (Cinema cinema : cinemas)
                System.out.println(cinema);
        } else
            System.out.println("No cinemas in the list.");

        System.out.println();
    }

    public void lookupAllMovies(List<Cinema> cinemas) {
        Set<Movie> allMovies = new TreeSet<>(new MovieNameComparator());

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();

            for (CinemaHall cinemaHall : cinemaHalls) {
                Set<Movie> movieSet = new HashSet<>(cinemaHall.getSessionMovieMap().values());
                allMovies.addAll(movieSet);
            }
        }

        System.out.println();

        if (!allMovies.isEmpty()) {
            for (Movie movie : allMovies)
                System.out.println(movie);
        } else
            System.out.println("No movies found.");

        System.out.println();
    }

    public void lookupAllMovieSessions(List<Cinema> cinemas) {
        Map<MovieSession, Movie> allMovieSessions = new HashMap<>();

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();

            for (CinemaHall cinemaHall : cinemaHalls) {
                Map<MovieSession, Movie> map = cinemaHall.getSessionMovieMap();
                allMovieSessions.putAll(map);
            }
        }

        System.out.println();

        if (!allMovieSessions.isEmpty()) {
            for (Map.Entry<MovieSession, Movie> pair : allMovieSessions.entrySet())
                System.out.println(pair.getKey() + " - " + pair.getValue());
        } else
            System.out.println("No movie sessions found.");

        System.out.println();
    }

    public void lookupMovieSessionsByMovie(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie name: ");
        String movieName = sc.nextLine();

        boolean movieSessionFound = false;

        System.out.println();

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();

            for (CinemaHall cinemaHall : cinemaHalls) {
                Map<MovieSession, Movie> map = cinemaHall.getSessionMovieMap();

                for (Map.Entry<MovieSession, Movie> pair : map.entrySet()) {
                    if (pair.getValue().getName().equalsIgnoreCase(movieName)) {
                        System.out.println(pair.getKey() + " - " + pair.getValue());
                        movieSessionFound = true;
                    }
                }
            }
        }

        if (!movieSessionFound)
            System.out.println("No such movie session found.");

        System.out.println();
    }

    public void lookupCinemasByMovie(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie name: ");
        String movieName = sc.nextLine();

        System.out.print("Enter session date: ");
        String date = sc.nextLine();

        boolean cinemaFound = false;

        System.out.println();

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();

            for (CinemaHall cinemaHall : cinemaHalls) {
                Map<MovieSession, Movie> map = cinemaHall.getSessionMovieMap();

                for (Map.Entry<MovieSession, Movie> pair : map.entrySet()) {
                    if (pair.getValue().getName().equalsIgnoreCase(movieName)
                            && pair.getKey().getDate().equalsIgnoreCase(date)) {
                        System.out.println(cinema);
                        cinemaFound = true;
                    }
                }
            }
        }

        if (!cinemaFound)
            System.out.println("No such cinema found.");

        System.out.println();
    }

    public void buyTicket(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie name: ");
        String movieName = sc.nextLine();

        System.out.print("Enter session date: ");
        String date = sc.nextLine();

        System.out.print("Enter session time: ");
        String time = sc.nextLine();

        boolean ticketFound = false;

        System.out.println();

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();
            Set<Visitor> visitors = cinema.getVisitors();

            for (CinemaHall cinemaHall : cinemaHalls) {
                Map<MovieSession, Movie> map = cinemaHall.getSessionMovieMap();

                for (Map.Entry<MovieSession, Movie> pair : map.entrySet()) {
                    if (pair.getValue().getName().equalsIgnoreCase(movieName)
                            && pair.getKey().getDate().equalsIgnoreCase(date)
                            && pair.getKey().getTime().equalsIgnoreCase(time)) {
                        System.out.println("You purchased ticket on " + pair.getKey().getDate()
                                + ", " + pair.getKey().getTime() + ", movie \""
                                + pair.getValue().getName() + "\" for $15");

                        ticketFound = true;
                        balance -= 15;

                        if (balance > 0)
                            sessionMovieMap.put(pair.getKey(), pair.getValue());
                        else
                            System.out.println("Not enough money for the ticket :(");

                        if (!visitors.contains(this)) {
                            hasTicket = true;
                            visitors.add(this);
                            cinema.setVisitors(visitors);
                        }
                    }
                }
            }
        }

        if (!ticketFound)
            System.out.println("No such ticket found.");

        System.out.println();
    }

    public void viewAllTickets() {
        System.out.println();

        if (hasTicket) {
            for (Map.Entry<MovieSession, Movie> pair : sessionMovieMap.entrySet())
                System.out.println("ticket -> " + pair.getKey() + " - " + pair.getValue());
        } else
            System.out.println("No purchased tickets.");

        System.out.println();
    }

    public void checkBalance() {
        System.out.println();

        if (balance > 0)
            System.out.println("Balance: $" + balance);
        else
            System.out.println("No cash left.");

        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(firstName, visitor.firstName) &&
                Objects.equals(lastName, visitor.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hasTicket=" + hasTicket +
                ", sessionMovieMap=" + sessionMovieMap +
                '}';
    }
}
