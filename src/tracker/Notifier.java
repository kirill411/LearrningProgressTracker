package tracker;

import java.util.*;

abstract class Notifier {
    private static final Map<User, String> users = new HashMap<>();

    private static final Formatter f = new Formatter(System.out);

    static void add(User user, String trackName){
        if (users.containsKey(user)) {
            users.put(user, users.get(user) + " " + trackName);
        } else {
            users.put(user, trackName);
        }
    }

    static void dump() {
        for (Map.Entry<User, String> entry : users.entrySet()) {
            User user = entry.getKey();
            String[] tracks = entry.getValue().split(" ");

            for (String track : tracks) {
                f.format("To: " + user.getEmail() + '\n');
                f.format("Re: Your Learning Progress%n");
                f.format("Hello, %s! You have accomplished our %s course!%n", user.getFullName(), track);
            }
        }
        f.format("Total %d students have been notified.%n", users.size());
        users.clear();
    }
}
