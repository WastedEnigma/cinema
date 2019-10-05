package cinema.people;

import cinema.Cinema;
import cinema.sessions.Movie;
import cinema.sessions.MovieSession;
import cinema.structure.CinemaHall;
import comparators.VisitorNameComparator;

import java.util.*;

import static generators.MovieSessionGenerator.*;

public class Administrator {

    public Administrator() {
    }

    public void addCinema(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter cinema name: ");
        String cinemaName = sc.nextLine();

        System.out.print("How many cinema halls required? ");
        int cinemaHallCount = sc.nextInt();
        int cinemaHallNumber;

        List<CinemaHall> cinemaHalls = new ArrayList<>();

        for (int i = 0; i < cinemaHallCount; i++) {
            System.out.print("Enter number of cinema hall: ");
            cinemaHallNumber = sc.nextInt();
            cinemaHalls.add(new CinemaHall(cinemaHallNumber));
        }

        System.out.print("Enter number of sessions to generate: ");
        int sessionCount = sc.nextInt();

        for (CinemaHall cinemaHall : cinemaHalls)
            cinemaHall.setSessionMovieMap(generateMovieSessionMap(sessionCount));

        Cinema cinema = new Cinema(cinemaName, cinemaHalls);

        System.out.println();

        if (!cinemas.contains(cinema)) {
            cinemas.add(cinema);
            System.out.println("\"" + cinema.getName() + "\" was added to the list");
        } else
            System.out.println("This cinema is already in the list.");

        System.out.println();
    }

    public void deleteCinema(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter cinema name: ");
        String cinemaName = sc.nextLine();

        boolean cinemaDeleted = false;
        Iterator<Cinema> cinemaIterator = cinemas.iterator();

        System.out.println();

        while (cinemaIterator.hasNext()) {
            Cinema cinema = cinemaIterator.next();
            String name = cinema.getName();

            if (name.equalsIgnoreCase(cinemaName)) {
                cinemaIterator.remove();
                System.out.println("\"" + name + "\" was successfully deleted");
                cinemaDeleted = true;
            }
        }

        if (!cinemaDeleted)
            System.out.println("Cannot delete non-existent cinema.");

        System.out.println();
    }

    public void addMovieSession(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie name: ");
        String movieName = sc.nextLine();

        System.out.print("Enter session date: ");
        String date = sc.nextLine();

        System.out.print("Enter session time: ");
        String time = sc.nextLine();

        System.out.print("Enter cinema name: ");
        String cinemaName = sc.nextLine();

        System.out.print("Enter cinema hall number: ");
        int cinemaHallNumber = sc.nextInt();

        boolean sessionAdded = false;

        System.out.println();

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();

            for (CinemaHall cinemaHall : cinemaHalls) {
                if (cinema.getName().equalsIgnoreCase(cinemaName)
                        && cinemaHall.getNumber() == cinemaHallNumber) {
                    MovieSession movieSession = new MovieSession(date, time);
                    Movie movie = new Movie(movieName);

                    cinemaHall.getSessionMovieMap().putIfAbsent(movieSession, movie);
                    System.out.println("Movie session \"" + movie.getName()
                            + "\" on " + movieSession.getDate()
                            + ", " + movieSession.getTime()
                            + " was added to the map");

                    sessionAdded = true;
                }
            }
        }

        if (!sessionAdded)
            System.out.println("Failed to add movie session.");

        System.out.println();
    }

    public void deleteMovieSession(List<Cinema> cinemas) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter movie name: ");
        String movieName = sc.nextLine();

        System.out.print("Enter session date: ");
        String date = sc.nextLine();

        System.out.print("Enter session time: ");
        String time = sc.nextLine();

        boolean movieSessionDeleted = false;

        System.out.println();

        for (Cinema cinema : cinemas) {
            List<CinemaHall> cinemaHalls = cinema.getCinemaHalls();

            for (CinemaHall cinemaHall : cinemaHalls) {
                Map<MovieSession, Movie> map = cinemaHall.getSessionMovieMap();
                Iterator<Map.Entry<MovieSession, Movie>> iterator = map.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<MovieSession, Movie> next = iterator.next();
                    Movie m = next.getValue();
                    MovieSession ms = next.getKey();
                    String n = m.getName();
                    String d = ms.getDate();
                    String t = ms.getTime();

                    if (movieName.equalsIgnoreCase(n)
                            && date.equalsIgnoreCase(d)
                            && time.equalsIgnoreCase(t)) {
                        iterator.remove();
                        System.out.println("Session on " + d + ", "
                                + t + ", \""
                                + n +"\" was successfully deleted");
                        movieSessionDeleted = true;
                        deleteVisitorMovieSession(cinema, n, d, t);
                    }
                }
            }
        }

        if (!movieSessionDeleted)
            System.out.println("Cannot deleted non-existent movie session.");

        System.out.println();
    }

    public void viewAllVisitorsWithTickets(List<Cinema> cinemas) {
        boolean visitorFound = false;
        Set<Visitor> visitorSet = new TreeSet<>(new VisitorNameComparator());

        System.out.println();

        for (Cinema cinema : cinemas) {
            Set<Visitor> visitors = cinema.getVisitors();

            if (!visitors.isEmpty()) {
                for (Visitor visitor : visitors) {
                    if (visitor.hasTicket()) {
                        visitorSet.add(visitor);
                        visitorFound = true;
                    }
                }
            }
        }

        for (Visitor visitor : visitorSet)
            System.out.println(visitor);

        if (!visitorFound)
            System.out.println("No visitors with tickets.");

        System.out.println();
    }

    private void deleteVisitorMovieSession(Cinema cinema, String movieName, String date, String time) {
        for (Visitor visitor : cinema.getVisitors()) {
            Iterator<Map.Entry<MovieSession, Movie>> msIterator = visitor.getSessionMovieMap().entrySet().iterator();

            while (msIterator.hasNext()) {
                Map.Entry<MovieSession, Movie> entry = msIterator.next();
                MovieSession movieSession = entry.getKey();
                Movie movie = entry.getValue();

                if (movie.getName().equalsIgnoreCase(movieName)
                        && movieSession.getDate().equalsIgnoreCase(date)
                        && movieSession.getTime().equalsIgnoreCase(time)) {
                    msIterator.remove();

                    if (visitor.getSessionMovieMap().size() < 1)
                        visitor.setHasTicket(false);
                }
            }
        }
    }
}
