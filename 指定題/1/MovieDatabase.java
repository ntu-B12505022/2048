import java.util.*;

public class MovieDatabase {
    private static Map<String, Movie> movies = new HashMap<>();
    private static Map<String, List<String>> movieSchedules = new HashMap<>();

    /**
     * 新增電影
     */
    public static boolean addMovie(String name, String description, String rating, int duration) {
        String movieId = "MOV" + (movies.size() + 1);
        Movie movie = new Movie(movieId, name, duration, description, rating);
        movies.put(movieId, movie);
        movieSchedules.put(movieId, new ArrayList<>());
        return true;
    }

    /**
     * 刪除電影
     */
    public static boolean removeMovie(String movieId) {
        if (movies.containsKey(movieId)) {
            movies.remove(movieId);
            movieSchedules.remove(movieId);
            return true;
        }
        return false;
    }

    /**
     * 修改電影場次時間（示範版，不含放映廳）
     */
    public static boolean updateSchedule(String movieId, String newTime) {
        if (!movies.containsKey(movieId)) return false;

        if (!newTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) return false;

        for (List<String> schedules : movieSchedules.values()) {
            if (schedules.contains(newTime)) {
                return false;
            }
        }

        movieSchedules.get(movieId).add(newTime);
        return true;
    }

    /**
     * 刪除電影場次時間
     */
    public static boolean removeSchedule(String movieId, String time) {
        if (!movies.containsKey(movieId)) return false;
        List<String> schedules = movieSchedules.get(movieId);
        return schedules.remove(time);
    }

    /**
     * 查詢所有上映中電影
     */
    public static List<Movie> getMovies() {
        return new ArrayList<>(movies.values());
    }

    /**
     * 查詢單一電影
     */
    public static Movie getMovie(String movieId) {
        return movies.get(movieId);
    }

    /**
     * 查詢電影場次
     */
    public static List<String> getSchedules(String movieId) {
        return movieSchedules.getOrDefault(movieId, Collections.emptyList());
    }
}
