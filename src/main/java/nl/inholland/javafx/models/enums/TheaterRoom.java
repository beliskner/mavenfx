package nl.inholland.javafx.models.enums;

public enum TheaterRoom {
    ROOM_1("Room 1"),
    ROOM_2("Room 2");

    private String string;

    TheaterRoom(String roomName) {
        this.string = roomName;
    }

    @Override
    public String toString() {
        return string;
    }
}
