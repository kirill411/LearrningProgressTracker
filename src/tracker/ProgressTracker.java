package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class ProgressTracker {
    final static Set<User> userSet = new HashSet<>();
    final Map<String, User> users = new HashMap<>();

    ProgressTracker() {
        System.out.println("Learning Progress Tracker");
    }

    void mainMenu() {
        while (true) {
            String userInput = Input.get().toLowerCase();
            if (userInput.isBlank()) {
                print("No input.");
                continue;
            }
            switch (userInput) {
                case "add students" -> subMenu(Command.ADD_STUDENT);
                case "add points" -> subMenu(Command.ADD_POINTS);
                case "find" -> subMenu(Command.FIND);
                case "statistics" -> subMenu(Command.STATISTICS);
                case "notify" -> Notifier.dump();
                case "exit" -> {
                    print("Bye!");
                    System.exit(0);
                }
                case "back" -> print("Enter 'exit' to exit the program.");
                case "list" -> list();
                default -> print("Unknown command!");
            }
        }
    }

    void subMenu(Command type) {
        switch (type) {
            case FIND -> print("Enter an id or 'back' to return:");
            case ADD_POINTS -> print("Enter an id and points or 'back' to return:");
            case ADD_STUDENT -> print("Enter student credentials or 'back' to return:");
            case STATISTICS -> {
                print("Type the name of a course to see details or 'back' to quit:");
                Statistics.dump();
            }
        }

        while (true) {
            String s = Input.get();

            if (s.equals("back")) {
                break;
            }

            switch (type) {
                case FIND -> find(s);
                case ADD_POINTS -> addPoints(s);
                case ADD_STUDENT -> addStudents(s);
                case STATISTICS -> statistics(s);
            }
        }

        if (type == Command.ADD_STUDENT) {
            print("Total " + userSet.size() + " students have been added.");
        }
    }

    void addStudents(String s) {
        if (s.split("\\s+").length < 3) {
            printIncorrect("credentials");
            return;
        }

        String firstName = s.substring(0, s.indexOf(" "));
        String lastName = s.substring(s.indexOf(" ") + 1, s.lastIndexOf(" "));
        String email = s.substring(s.lastIndexOf(" ") + 1);

        if (Input.notAName(firstName)) {
            printIncorrect("first name");
            return;
        } else if (Input.notAName(lastName)) {
            printIncorrect("last name");
            return;
        } else if (Input.notAnEmail(email)) {
            printIncorrect("email");
            return;
        }

        User user = new User(firstName, lastName, email);

        if (!userSet.add(user)) {
            print("This email is already taken.");
            return;
        }

        users.put(user.getId() + "", user);
        print("The student have been added.");
    }

    void addPoints(String input) {
        if (input.split(" ").length != 5) {
            printIncorrect("points format");
            return;
        }

        String id = input.substring(0, input.indexOf(' '));
        User user = users.get(id);

        if (user == null) {
            print("No student is found for id=" + id + ".");
            return;
        }

        String points = input.substring(input.indexOf(' ') + 1);

        if (Input.notAPoints(points)) {
            printIncorrect("points format");
            return;
        }

        Track.addPoints(user, points);
        print("Points updated.");
    }

    void statistics(String s) {
        try {
            Statistics.getTrackStatistics(TrackName.valueOf(s.toUpperCase()).ordinal());
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown course.");
        }
    }

    void find(String id) {
        User user = users.get(id);

        if (user == null) {
            print("No student is found for id=" + id + ".");
            return;
        }

        print(user.toString());
    }

    void list() {
        if (userSet.size() == 0) {
            print("No students found.");
        } else {
            print("Students:");
            var list = new ArrayList<>(userSet);
            list.sort(Comparator.comparing(User::getId));
            print(list.stream().map(s -> s.getId() + "").collect(Collectors.joining("\n")));
        }
    }

    private void print(String s) {
        System.out.println(s);
    }

    private void printIncorrect(String msg) {
        System.out.println("Incorrect " + msg + ".");
    }
}

enum Command {
    ADD_STUDENT, ADD_POINTS, FIND, STATISTICS
}
