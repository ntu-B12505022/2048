import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class StaffMainMenuFrame extends JFrame {
    private Staff staff;

    public StaffMainMenuFrame(Staff staff) {
        this.staff = staff;

        setTitle("營運人員系統");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton bookingStatusButton = new JButton("訂票狀態管理");
        JButton movieManageButton = new JButton("電影管理");
        JButton logoutButton = new JButton("登出");

        bookingStatusButton.addActionListener(e -> new BookingStatusFrame().setVisible(true));
        movieManageButton.addActionListener(e -> new MovieManageFrame().setVisible(true));

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 關閉當前主畫面
                LoginFrame login = new LoginFrame(); // 返回登入畫面
                login.setVisible(true); // 顯示登入畫面
            }
        });

        panel.add(bookingStatusButton);
        panel.add(movieManageButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}
