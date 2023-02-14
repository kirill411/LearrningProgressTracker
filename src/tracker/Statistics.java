package tracker;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class Statistics {
    private static final Formatter F = new Formatter(System.out);
    static List<Track> tracks = new ArrayList<>(Track.tracks);

    static List<Track> mp = new ArrayList<>();
    static List<Track> ha = new ArrayList<>();
    static List<Track> ec = new ArrayList<>();

    public static void getTrackStatistics(int i) {
        Track.tracks.get(i).students.sort(Comparator
                .comparingInt(Student::getPoints).reversed()
                .thenComparingInt(s -> s.getUser().getId()));

        F.format(Track.tracks.get(i).name + '\n');
        F.format("%-9s %-9s %-9s%n", "id", "points", "completed");
        Track.tracks.get(i).students.forEach(s -> F.format("%-9s %-9s %-9s%n",
                s.getUser().getId(), s.getPoints(), Track.tracks.get(i).getCompletePercent(s) + "%"));
    }

    public static void dump() {
        F.format("Most popular: " + parse(mostPopularTrack())  + '\n');
        F.format("Least popular: " + parse(leastPopularTrack()) + '\n');
        F.format("Highest activity: " + parse(highActivityTrack()) + '\n');
        F.format("Lowest activity: " + parse(lowActivityTrack()) + '\n');
        F.format("Easiest course: " + parse(easiestTrack()) + '\n');
        F.format("Hardest course: " + parse(hardestTrack()) + '\n');
    }

    private static String parse(String s) {
        return isAvailable() || s.isEmpty() ? "n/a" : s;
    }

    private static String mostPopularTrack() {
        tracks.sort(Comparator.comparingInt(Track::getEnrolled).reversed());
        mp.clear();
        tracks.stream().filter(t -> t.getEnrolled() == tracks.get(0).getEnrolled())
                .forEach(mp::add);

        return mp.stream().map(t -> t.name).collect(Collectors.joining(", "));
    }

    private static String leastPopularTrack() {
        tracks.sort(Comparator.comparing(Track::getEnrolled));
        List<Track> lp = new ArrayList<>();
        tracks.stream().filter(t -> t.getEnrolled() == tracks.get(0).getEnrolled())
                .filter(t -> !mp.contains(t))
                .forEach(lp::add);
        return lp.stream().map(t -> t.name).collect(Collectors.joining(", "));
    }

    private static String highActivityTrack() {
        tracks.sort(Comparator.comparing(Track::getSubmissions).reversed());
        ha.clear();
        tracks.stream().filter(t -> t.getSubmissions() == tracks.get(0).getSubmissions())
                .forEach(ha::add);
        return ha.stream().map(t -> t.name).collect(Collectors.joining(", "));
    }

    private static String lowActivityTrack() {
        tracks.sort(Comparator.comparing(Track::getSubmissions));
        List<Track> la = new ArrayList<>();
        tracks.stream().filter(t -> t.getEnrolled() == tracks.get(0).getEnrolled())
                .filter(t -> !ha.contains(t))
                .forEach(la::add);
        return la.stream().map(t -> t.name).collect(Collectors.joining(", "));
    }

    private static String easiestTrack() {
        tracks.sort(Comparator.comparingInt(Track::getAverageScore).reversed());
        ec.clear();
        tracks.stream().filter(t -> t.getAverageScore() == tracks.get(0).getAverageScore())
                .forEach(ec::add);

        return ec.stream().map(t -> t.name).collect(Collectors.joining(", "));
    }

    private static String hardestTrack() {
        tracks.sort(Comparator.comparing(Track::getAverageScore));
        List<Track> hc = new ArrayList<>();
        tracks.stream().filter(t -> t.getAverageScore() == tracks.get(0).getAverageScore())
                .filter(t -> !ec.contains(t))
                .forEach(hc::add);
        return hc.stream().map(t -> t.name).collect(Collectors.joining(", "));
    }
    private static boolean isAvailable() {
        return tracks.stream().noneMatch(t -> t.students.size() > 0);
    }
}
