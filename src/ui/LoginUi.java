package ui;
import bean.User;

import javax.swing.Box;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
//1.自定义登录界面
public class LoginUi extends JFrame implements ActionListener {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JPasswordField regConfirmPasswordField;
    private JTextField regEmailField;

    private static final Color PRIMARY_COLOR = new Color(64, 128, 255);
    private static final Color PRIMARY_HOVER = new Color(50, 110, 230);
    private static final Color BG_LIGHT = new Color(240, 244, 255);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color TEXT_GRAY = new Color(127, 140, 155);
    private static final Color INPUT_BG = new Color(248, 249, 252);
    private static final Color INPUT_BORDER = new Color(218, 225, 236);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
//定义一个静态的集合，存储系统中全部的用户对象信息。
    private static final java.util.List<User> users = new java.util.ArrayList<>();
//    private static final java.util.List<User> users = new java.util.ArrayList<>();

//初始化几个用户信息。
    static {
        users.add(new User("zhangsan", "123456"));
        users.add(new User("lisi", "123456"));
        users.add(new User("wangwu", "123456"));
        users.add(new User("zhaoliu", "123456"));
    }

    public LoginUi() {
        setTitle("人事管理系统");
        setSize(480, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_LIGHT);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");

        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
//登录时，添加可以显示密码的选项，点击后可以看到已经输入的密码。
    private void togglePasswordVisibility(JPasswordField passwordField) {
        if (passwordField.getEchoChar() == '\u0000') {
            passwordField.setEchoChar('\u2022');
        } else {
            passwordField.setEchoChar('\u0000');
        }
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), getHeight(), new Color(100, 160, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("👤 人事管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel("Human Resource Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        subtitleLabel.setForeground(new Color(220, 230, 255));
        header.add(subtitleLabel, BorderLayout.SOUTH);

        header.setBorder(new EmptyBorder(12, 0, 8, 0));
        return header;
    }

    private JPanel createLoginPanel() {
        JPanel panel = createBasePanel();

        panel.add(createTitleLabel("用户登录", "欢迎回来，请输入您的账号信息"));

        loginUsernameField = createStyledTextField("请输入用户名");
        loginPasswordField = createStyledPasswordField("请输入密码");

        panel.add(createFieldWrapper("用户名", loginUsernameField));
        panel.add(createFieldWrapper("密  码", loginPasswordField));

        JPanel optionPanel = new JPanel(new BorderLayout());
        optionPanel.setOpaque(false);
        optionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JCheckBox rememberBox = new JCheckBox("记住密码");
        rememberBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        rememberBox.setForeground(TEXT_GRAY);
        rememberBox.setOpaque(false);
        rememberBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        optionPanel.add(rememberBox, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JCheckBox showPasswordBox = new JCheckBox("显示密码");
        showPasswordBox.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        showPasswordBox.setForeground(TEXT_GRAY);
        showPasswordBox.setOpaque(false);
        showPasswordBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showPasswordBox.addActionListener(e -> togglePasswordVisibility(loginPasswordField));
        showPasswordBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel forgotLabel = new JLabel("忘记密码？");
        forgotLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        forgotLabel.setForeground(PRIMARY_COLOR);
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(showPasswordBox);
        rightPanel.add(Box.createVerticalStrut(2));
        rightPanel.add(forgotLabel);
        optionPanel.add(rightPanel, BorderLayout.EAST);

        panel.add(optionPanel);
        panel.add(Box.createVerticalStrut(10));

        JButton loginBtn = createGradientButton("登  录");
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn);

        panel.add(Box.createVerticalStrut(15));
        panel.add(createSwitchLink("还没有账号？立即注册", "REGISTER"));

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = createBasePanel();

        panel.add(createTitleLabel("用户注册", "创建新账号，开始使用系统"));

        regUsernameField = createStyledTextField("请输入用户名");
        regEmailField = createStyledTextField("请输入邮箱");
        regPasswordField = createStyledPasswordField("请输入密码");
        regConfirmPasswordField = createStyledPasswordField("请再次输入密码");

        panel.add(createFieldWrapper("用户名", regUsernameField));
        panel.add(createFieldWrapper("邮  箱", regEmailField));
        panel.add(createFieldWrapper("密  码", regPasswordField));
        panel.add(createFieldWrapper("确认密码", regConfirmPasswordField));

        panel.add(Box.createVerticalStrut(5));

        JButton registerBtn = createGradientButton("注  册");
        registerBtn.addActionListener(e -> handleRegister());
        panel.add(registerBtn);

        panel.add(Box.createVerticalStrut(10));
        panel.add(createSwitchLink("已有账号？返回登录", "LOGIN"));

        return panel;
    }

    private JPanel createBasePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 40, 30, 40));
        return panel;
    }

    private JPanel createTitleLabel(String title, String subtitle) {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        subtitleLabel.setForeground(TEXT_GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitleLabel);
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        return titlePanel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(INPUT_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);
        field.setPreferredSize(new Dimension(0, 42));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setCaretColor(PRIMARY_COLOR);
        field.setMargin(new Insets(8, 12, 8, 12));
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(INPUT_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);
        field.setPreferredSize(new Dimension(0, 42));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setCaretColor(PRIMARY_COLOR);
        field.setMargin(new Insets(8, 12, 8, 12));
        return field;
    }

    private JPanel createFieldWrapper(String labelText, JComponent field) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, 13));
        label.setForeground(TEXT_DARK);
        label.setBorder(new EmptyBorder(0, 2, 6, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(label);
        wrapper.add(field);
        wrapper.add(Box.createVerticalStrut(12));
        return wrapper;
    }

    private JButton createGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(PRIMARY_HOVER);
                } else if (getModel().isRollover  ()) {
                    GradientPaint gp = new GradientPaint(0, 0, PRIMARY_HOVER, getWidth(), 0, PRIMARY_COLOR);
                    g2.setPaint(gp);
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, new Color(100, 160, 255));
                    g2.setPaint(gp);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.setColor(new Color(255, 255, 255, 60));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        button.setFont(new Font("微软雅黑", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(0, 45));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createSwitchLink(String text, String targetCard) {
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setOpaque(false);

        JLabel linkLabel = new JLabel("<html><span style='color:" + colorToHex(PRIMARY_COLOR) + "; text-decoration: underline; cursor: pointer;'>" + text + "</span></html>");
        linkLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, targetCard);
                if ("REGISTER".equals(targetCard)) {
                    setSize(480, 650);
                } else {
                    setSize(480, 580);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkLabel.setText("<html><span style='color:" + colorToHex(PRIMARY_HOVER) + "; text-decoration: underline; cursor: pointer;'>" + text + "</span></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkLabel.setText("<html><span style='color:" + colorToHex(PRIMARY_COLOR) + "; text-decoration: underline; cursor: pointer;'>" + text + "</span></html>");
            }
        });

        linkPanel.add(linkLabel);
        return linkPanel;
    }

    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword()).trim();

        if (username.isEmpty()) {
            showMessage("请输入用户名", false);
            loginUsernameField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showMessage("请输入密码", false);
            loginPasswordField.requestFocus();
            return;
        }

        boolean found = false;
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                found = true;
                break;
            }
        }

        if (!found) {
            showMessage("用户名或密码错误", false);
            return;
        }

        showMessage("登录成功！欢迎，" + username, true);
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            EmployManager manager = new EmployManager(username);
            manager.setVisible(true);
        });
    }

    private void handleRegister() {
        String username = regUsernameField.getText().trim();
        String email = regEmailField.getText().trim();
        String password = new String(regPasswordField.getPassword()).trim();
        String confirmPassword = new String(regConfirmPasswordField.getPassword()).trim();

        if (username.isEmpty()) {
            showMessage("请输入用户名", false);
            regUsernameField.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            showMessage("请输入邮箱", false);
            regEmailField.requestFocus();
            return;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showMessage("邮箱格式不正确", false);
            regEmailField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showMessage("请输入密码", false);
            regPasswordField.requestFocus();
            return;
        }
        if (password.length() < 6) {
            showMessage("密码长度不能少于6位", false);
            regPasswordField.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            showMessage("两次输入的密码不一致", false);
            regConfirmPasswordField.requestFocus();
            return;
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                showMessage("用户名已存在", false);
                regUsernameField.requestFocus();
                return;
            }
        }

        users.add(new User(username, password));

        regUsernameField.setText("");
        regEmailField.setText("");
        regPasswordField.setText("");
        regConfirmPasswordField.setText("");

        showMessage("注册成功！请登录", true);
        cardLayout.show(mainPanel, "LOGIN");
        setSize(480, 580);
    }

    private void showMessage(String msg, boolean success) {
        JOptionPane.showMessageDialog(this, msg, success ? "成功" : "提示",
                success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }
    //登录成功后，进入到EmployManager页面。
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("登录")) {
                handleLogin();

            } else if (button.getText().equals("注册")) {
                handleRegister();
            }
        }
    }

    private String colorToHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            LoginUi ui = new LoginUi();
            ui.setVisible(true);
        });
    }
}
