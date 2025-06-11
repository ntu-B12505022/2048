import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BookingFrame extends JFrame{
	private Member member;

    public BookingFrame(Member member) {
        this.member = member;
        setTitle("訂票");
        setSize(500, 400);
        setLocationRelativeTo(null);

        List<Movie> movies = MovieDatabase.getMovies();
        JComboBox<String> movieComboBox = new JComboBox<>();
        for (Movie movie : movies) {
            movieComboBox.addItem(movie.getMovieId() + " - " + movie.getName());
        }

        JTextField showTimeField = new JTextField(20);
        JTextField ticketField = new JTextField(5);
        JButton bookButton = new JButton("確認訂票");

        bookButton.addActionListener(e -> {
            String selected = (String) movieComboBox.getSelectedItem();
            String movieId = selected.split(" - ")[0];
            String showTime = showTimeField.getText();
            int numberOfTickets = Integer.parseInt(ticketField.getText());

            Booking booking = BookingSystem.bookTicket(member, movieId, showTime, numberOfTickets);
            if (booking != null) {
                JOptionPane.showMessageDialog(this, "訂票成功！\n訂票ID: " + booking.getBookingId());
            } else {
                JOptionPane.showMessageDialog(this, "訂票失敗，請檢查資料或座位是否已滿。");
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("選擇電影:"));
        panel.add(movieComboBox);
        panel.add(new JLabel("輸入場次時間 (yyyy-MM-dd HH:mm):"));
        panel.add(showTimeField);
        panel.add(new JLabel("輸入票數:"));
        panel.add(ticketField);
        panel.add(bookButton);

        add(panel, BorderLayout.CENTER);
    }
}
