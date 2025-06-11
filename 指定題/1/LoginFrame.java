import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    private static Map<String, Member> memberMap = new HashMap<>();
    private static Map<String, Staff> staffMap = new HashMap<>();

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (memberMap.containsKey(email)) {
            Member member = memberMap.get(email);
            if (member.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "會員登入成功！");
                new MemberMainMenuFrame(member).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "會員密碼錯誤！");
            }
        } else if (staffMap.containsKey(email)) {
            Staff staff = staffMap.get(email);
            if (staff.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "營運人員登入成功！");
                new StaffMainMenuFrame(staff).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "營運人員密碼錯誤！");
            }
        } else {
            JOptionPane.showMessageDialog(this, "帳號不存在！");
        }
    }

    private void handleRegister() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        String birthDateStr = JOptionPane.showInputDialog(this, "請輸入出生年月日 (YYYY-MM-DD)：");
        if (birthDateStr == null || birthDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入有效的出生年月日！");
            return;
        }

        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "日期格式錯誤，請使用 YYYY-MM-DD 格式！");
            return;
        }

        if (memberMap.containsKey(email) || staffMap.containsKey(email)) {
            JOptionPane.showMessageDialog(this, "此帳號已被註冊！");
            return;
        }

        String uid = "M" + (memberMap.size() + 1);
        Member newMember = new Member(uid, email, password, birthDate);
        memberMap.put(email, newMember);
        JOptionPane.showMessageDialog(this, "註冊成功，請重新登入！");
    }

    public LoginFrame() {
        setTitle("登入");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("登入");
        registerButton = new JButton("註冊");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("email:"));
        panel.add(emailField);
        panel.add(new JLabel("密碼:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        staffMap.put("staff@cinema.com", new Staff("S001", "staff@cinema.com", "staff123"));

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
    }
}
