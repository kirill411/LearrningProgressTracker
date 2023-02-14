package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Track {
    final String name;
    final int pointsToComplete;
    int pointsTotal;
    int submissions = 0;

    List<Student> students = new ArrayList<>();
    static List<Track> tracks = new ArrayList<>();

    static {
        Stream.of(TrackName.values()).forEach(t -> tracks.add(new Track(t.name, t.pointsToComplete)));
    }

    Track(String name, int pointsToComplete) {
        this.name = name;
        this.pointsToComplete = pointsToComplete;
    }

    static void addPoints(User user ,String points) {
        int[] ints = Arrays.stream(points.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        IntStream.range(0, ints.length)
                .filter(i -> ints[i] > 0)
                .forEach(i -> tracks.get(i).addPoints(user, ints[i]));
    }

    private void addPoints(User user, int points) {
        Student student = students.stream().filter(s -> s.getUser().equals(user)).findFirst().orElse(null);

        if (student == null) {
            student = new Student(user);
            students.add(student);
        }

        student.addPoints(points);
        submissions++;
        setCompleted(student);
        pointsTotal += points;
    }

    int getEnrolled() {
        return students.size();
    }

    int getSubmissions() {
        return submissions;
    }

    int getAverageScore() {
        return pointsTotal / (getEnrolled() == 0 ? 1 : getEnrolled());
    }

    public String getCompletePercent(Student student) {
        return BigDecimal.valueOf((double) student.getPoints() / pointsToComplete * 100)
                .setScale(1, RoundingMode.HALF_UP).toString();
    }

    public void setCompleted(Student student) {
        if (!student.isCompleted()) {
            student.setCompleted(student.getPoints() >= pointsToComplete);
            Notifier.add(student.getUser(), this.name);
        }
    }
}

enum TrackName {
    JAVA("Java", 600), DSA("DSA", 400),
    DATABASES("Databases", 480), SPRING("Spring", 550);

    final String name;
    final int pointsToComplete;

    TrackName(String name, int pointsToComplete) {
        this.name = name;
        this.pointsToComplete = pointsToComplete;
    }
}
