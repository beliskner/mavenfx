package nl.inholland.javafx.data;

import nl.inholland.javafx.models.Movie;
import nl.inholland.javafx.models.Room;
import nl.inholland.javafx.models.Showing;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.models.enums.TheaterRoom;
import nl.inholland.javafx.models.enums.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private List<Showing> showings = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Showing> getShowings() {
        return showings;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Database() {
        // Add user
        User wim = new User("wimmelstein", "luvjava", UserRole.USER);
        users.add(wim);
        // Add admin
        User mark = new User("ahrnuld", "csharpisbeter", UserRole.ADMIN);
        users.add(mark);
        // Add test
        User test = new User("a", "a", UserRole.USER);
        users.add(test);

        // Add rooms
        Room room1 = new Room(TheaterRoom.ROOM_1, 200);
        Room room2= new Room(TheaterRoom.ROOM_2, 150);
        rooms.add(room1);
        rooms.add(room2);

        // Add movies
        Movie movie1 = new Movie("Fjord of the Kings: The Return of the Thing", 9.00, 200);
        movies.add(movie1);
        Movie movie2 = new Movie("Duin", 12.00, 155);
        movies.add(movie2);

        // Add showings
        Showing showing3 = new Showing(movie2, room1, LocalDateTime.of(2021, 11, 1,17,30,0));
        showings.add(showing3);
        Showing showing1 = new Showing(movie1, room1, LocalDateTime.of(2021, 11, 1,20,30,0));
        showings.add(showing1);
        Showing showing4 = new Showing(movie1, room2, LocalDateTime.of(2021, 11, 1,16,30,0));
        showings.add(showing4);
        Showing showing2 = new Showing(movie2, room2, LocalDateTime.of(2021, 11, 1,20,30,0));
        showings.add(showing2);
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }

    public Boolean addShowing(Showing showing){
        for (Showing showingInDB : showings) {
            LocalDateTime lower = showingInDB.getStartTime().minusMinutes(15);
            LocalDateTime upper = showingInDB.getEndTime().plusMinutes(15);
            if ((showing.getStartTime().isAfter(lower) || showing.getStartTime().equals(lower)) && (showing.getStartTime().isBefore(upper) || showing.getStartTime().equals(upper))) {
                return false;
            }
            if ((showing.getEndTime().isAfter(lower) || showing.getEndTime().equals(lower)) && (showing.getEndTime().isBefore(upper) || showing.getEndTime().equals(upper))) {
                return false;
            }
        }
        showings.add(showing);
        return true;
    }

    public void updateShowing(Showing showing){
        Integer index = showings.indexOf(showing);
        showings.set(index, showing);
    }
}
