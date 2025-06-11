import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookingHistoryFrame extends JFrame{
	private Member member;

    public BookingHistoryFrame(Member member) {
        this.member = member;
        setTitle("訂票紀錄查詢");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextArea historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);

        List<Booking> bookings = BookingSystem.getBookingHistory(member);
        StringBuilder sb = new StringBuilder();
        for (Booking booking : bookings) {
            sb.append("訂票ID: ").append(booking.getBookingId()).append("\n")
              .append("電影ID: ").append(booking.getMovieId()).append("\n")
              .append("場次時間: ").append(booking.getShowTime()).append("\n")
              .append("票數: ").append(booking.getNumberOfTickets()).append("\n\n");
        }

        historyTextArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
