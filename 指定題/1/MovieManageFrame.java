import javax.swing.*;
import java.awt.*;

public class MovieManageFrame extends JFrame{
	public MovieManageFrame() {
        setTitle("電影管理");
        setSize(400, 400);
        setLocationRelativeTo(null);

        JButton addMovieButton = new JButton("上架電影");
        JButton removeMovieButton = new JButton("下架電影");
        JButton updateScheduleButton = new JButton("修改電影場次時間");

        addMovieButton.addActionListener(e -> {
            String movieName = JOptionPane.showInputDialog(this, "輸入電影名稱：");
            String description = JOptionPane.showInputDialog(this, "輸入電影簡介：");
            String rating = JOptionPane.showInputDialog(this, "輸入電影分級：");
            String duration = JOptionPane.showInputDialog(this, "輸入片長(分鐘)：");
            boolean success = MovieDatabase.addMovie(movieName, description, rating, Integer.parseInt(duration));
            if (success) {
                JOptionPane.showMessageDialog(this, "電影上架成功！");
            } else {
                JOptionPane.showMessageDialog(this, "電影上架失敗！");
            }
        });

        removeMovieButton.addActionListener(e -> {
            String movieId = JOptionPane.showInputDialog(this, "輸入電影ID：");
            boolean success = MovieDatabase.removeMovie(movieId);
            if (success) {
                JOptionPane.showMessageDialog(this, "電影下架成功！");
            } else {
                JOptionPane.showMessageDialog(this, "電影下架失敗！");
            }
        });

        updateScheduleButton.addActionListener(e -> {
            String movieId = JOptionPane.showInputDialog(this, "輸入電影ID：");
            String newTime = JOptionPane.showInputDialog(this, "輸入新的場次時間 (yyyy-MM-dd HH:mm)：");
            boolean success = MovieDatabase.updateSchedule(movieId, newTime);
            if (success) {
                JOptionPane.showMessageDialog(this, "電影場次時間更新成功！");
            } else {
                JOptionPane.showMessageDialog(this, "時間衝突或更新失敗！");
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(addMovieButton);
        panel.add(removeMovieButton);
        panel.add(updateScheduleButton);

        add(panel, BorderLayout.CENTER);
    }
}

