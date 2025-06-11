import java.awt.*;
import javax.swing.*;

public class CancelBookingFrame extends JFrame {
    private Member member;

    public CancelBookingFrame(Member member) {
        this.member = member;
        setTitle("退票");
        setSize(400, 200);
        setLocationRelativeTo(null);

        // 建立元件
        JLabel bookingIdLabel = new JLabel("輸入訂票紀錄ID:");
        JTextField bookingIdField = new JTextField(20);
        JButton cancelButton = new JButton("確認退票");

        // 按鈕事件
        cancelButton.addActionListener(e -> {
            String bookingId = bookingIdField.getText().trim();
            boolean success = BookingSystem.cancelBooking(member, bookingId);
            if (success) {
                JOptionPane.showMessageDialog(this, "退票成功！");
            } else {
                JOptionPane.showMessageDialog(this, "退票失敗，請確認訂票紀錄ID是否正確或是否已超過退票期限。");
            }
        });

        // Panel 設計
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(bookingIdLabel, BorderLayout.WEST);
        inputPanel.add(bookingIdField, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(cancelButton, BorderLayout.SOUTH);

        add(mainPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
