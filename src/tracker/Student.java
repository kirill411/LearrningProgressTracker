package tracker;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

public class Student {
    private final User user;

    private int points = 0;

    private boolean completed = false;

    Student(User user) {
        this.user = user;
    }

    void addPoints(int points) {
        this.points += points;
    }

    int getPoints() {
        return points;
    }

    User getUser() {
        return user;
    }

    boolean isCompleted() {
        return completed;
    }

    void setCompleted(boolean b) {
        completed = b;
    }
}
