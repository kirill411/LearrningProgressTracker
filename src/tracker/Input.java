package tracker;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class Input {
    static final Scanner SCANNER = new Scanner(System.in);

    static final Pattern namePattern = Pattern.compile("([A-Za-z]+[-']?[A-Za-z]+([-']?[A-Za-z]+)*\\s?)+");
    static final Pattern emailPattern = Pattern.compile("[\\w.]{1,30}@[a-z0-9]{1,20}\\.[a-z0-9]{1,10}");
    static final Pattern pointsPattern = Pattern.compile("\\d+ \\d+ \\d+ \\d+");

    static String get() {
        return SCANNER.nextLine().trim();
    }

    static boolean notAName(String name) {
        Matcher m = namePattern.matcher(name);
        return !m.matches();
    }

    static boolean notAnEmail(String email) {
        Matcher m = emailPattern.matcher(email);
        return !m.matches();
    }

    static boolean notAPoints(String points) {
        Matcher m = pointsPattern.matcher(points);
        return !m.matches();
    }
}
