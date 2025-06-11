import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class MemberMainMenuFrame extends JFrame {
    private Member member;

    public MemberMainMenuFrame(Member member) {
        this.member = member;

        setTitle("主選單");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5列，最後一列放登出按鈕

        JButton movieListButton = new JButton("電影查詢");
        JButton bookingButton = new JButton("訂票");
        JButton cancelBookingButton = new JButton("退票");
        JButton historyButton = new JButton("訂票紀錄");
        JButton logoutButton = new JButton("登出");

        movieListButton.addActionListener(e -> new MovieListFrame().setVisible(true));
        bookingButton.addActionListener(e -> new BookingFrame(member).setVisible(true));
        cancelBookingButton.addActionListener(e -> new CancelBookingFrame(member).setVisible(true));
        historyButton.addActionListener(e -> new BookingHistoryFrame(member).setVisible(true));

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 關閉目前畫面
                LoginFrame login = new LoginFrame(); // 回到登入畫面
                login.setVisible(true); // 顯示登入畫面
            }
        });

        panel.add(movieListButton);
        panel.add(bookingButton);
        panel.add(cancelBookingButton);
        panel.add(historyButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}
