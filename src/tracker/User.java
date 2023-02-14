package tracker;

import java.util.Objects;

public class User {
    private static int idCounter = 0;
    private final int id = ++idCounter;

    private final String firstName;
    private final String lastName;
    private final String email;

    User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof User user)) {
            return false;
        }

        return user.getEmail().equals(this.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                id, getPoints(0), getPoints(1), getPoints(2), getPoints(3));
    }

    private int getPoints(int index) {
        int points = 0;
        for (Student s : Track.tracks.get(index).students) {
            if (s.getUser().equals(this)) {
                points = s.getPoints();
            }
        }
        return points;
    }
}
