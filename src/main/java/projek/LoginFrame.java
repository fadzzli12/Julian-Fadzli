package projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private final JTextField txtUsername = new JTextField(18);
    private final JPasswordField txtPassword = new JPasswordField(18);
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnBatal = new JButton("Batal");
    private final JLabel lblPesan = new JLabel(" ");
    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Login - Sistem Absensi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel judul = new JLabel("SISTEM ABSENSI SISWA");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        judul.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(judul, g);

        JLabel sub = new JLabel("Silakan login dulu");
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        sub.setForeground(Color.GRAY);
        g.gridy = 1;
        panel.add(sub, g);

        g.gridwidth = 1;
        g.gridy = 2; g.gridx = 0; g.weightx = 0;
        panel.add(new JLabel("Username"), g);
        g.gridx = 1; g.weightx = 1;
        panel.add(txtUsername, g);

        g.gridy = 3; g.gridx = 0; g.weightx = 0;
        panel.add(new JLabel("Password"), g);
        g.gridx = 1; g.weightx = 1;
        panel.add(txtPassword, g);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTombol.add(btnLogin);
        panelTombol.add(btnBatal);
        g.gridy = 4; g.gridx = 0; g.gridwidth = 2; g.weightx = 1;
        panel.add(panelTombol, g);

        lblPesan.setForeground(Color.RED);
        lblPesan.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = 5;
        panel.add(lblPesan, g);

        add(panel);

        btnLogin.addActionListener((ActionEvent e) -> doLogin());
        btnBatal.addActionListener((ActionEvent e) -> System.exit(0));
        txtPassword.addActionListener((ActionEvent e) -> doLogin());
        getRootPane().setDefaultButton(btnLogin);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblPesan.setText("Username dan password wajib diisi!");
            return;
        }

        btnLogin.setEnabled(false);
        lblPesan.setForeground(Color.BLUE);
        lblPesan.setText("Memproses...");

        User user = userDAO.login(username, password);

        if (user == null) {
            lblPesan.setForeground(Color.RED);
            lblPesan.setText("Username / password salah!");
            txtPassword.setText("");
            btnLogin.setEnabled(true);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Selamat datang, " + user.getUsername() + " (" + user.getRole() + ")",
                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        AttendanceGUI gui = new AttendanceGUI(user);
        gui.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
