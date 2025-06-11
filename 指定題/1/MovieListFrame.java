import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieListFrame extends JFrame {
	public MovieListFrame() {
        setTitle("電影查詢");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea movieTextArea = new JTextArea();
        movieTextArea.setEditable(false);

        List<Movie> movies = MovieDatabase.getMovies();
        StringBuilder sb = new StringBuilder();
        for (Movie movie : movies) {
            sb.append("名稱: ").append(movie.getName()).append("\n")
              .append("簡介: ").append(movie.getDescription()).append("\n")
              .append("分級: ").append(movie.getRating()).append("\n")
              .append("片長: ").append(movie.getDuration()).append(" 分鐘\n\n");
        }

        movieTextArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(movieTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
