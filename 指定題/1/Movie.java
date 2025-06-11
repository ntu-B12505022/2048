import java.util.ArrayList;
import java.util.List;

public class Movie {
	private String movieId;
    private String name;
    private int duration;
    private String description;
    private String rating;
    private List<String> schedules = new ArrayList<>();

    public Movie(String movieId, String name, int duration, String description, String rating) {
        this.movieId = movieId;
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }
    
    public List<String> getSchedules() {
        return schedules;
    }
}
