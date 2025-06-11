import javax.swing.*;
import java.awt.*;

public class BookingStatusFrame extends JFrame{
	public BookingStatusFrame() {
        setTitle("訂票狀態管理");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTextField movieIdField = new JTextField(10);
        JButton queryButton = new JButton("查詢訂票狀態");
        JButton modifyButton = new JButton("修改訂票狀態");

        queryButton.addActionListener(e -> {
            String movieId = movieIdField.getText();
            String status = BookingSystem.getBookingStatus(movieId);
            JOptionPane.showMessageDialog(this, "訂票狀態：\n" + status);
        });

        modifyButton.addActionListener(e -> {
            String movieId = movieIdField.getText();
            String newStatus = JOptionPane.showInputDialog(this, "請輸入新的訂票狀態：");
            boolean success = BookingSystem.updateBookingStatus(movieId, newStatus);
            if (success) {
                JOptionPane.showMessageDialog(this, "訂票狀態更新成功！");
            } else {
                JOptionPane.showMessageDialog(this, "訂票狀態更新失敗！");
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("電影場次ID:"));
        panel.add(movieIdField);
        panel.add(queryButton);
        panel.add(modifyButton);

        add(panel, BorderLayout.CENTER);
	}
}
