package learnify.gui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import learnify.models.Admin;
import javax.swing.border.EmptyBorder;

public class AdminDashboard extends JFrame {

    private final Admin admin;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        initializeUI();
    }

    // ---------- Rounded Shadow Panel ----------
    class CardPanel extends JPanel {
        private final Color bgColor;
        private final int cornerRadius = 20;

        public CardPanel(Color bgColor) {
            this.bgColor = bgColor;
            setOpaque(false);
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Drop shadow effect
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, cornerRadius, cornerRadius);

            // Main card
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, cornerRadius, cornerRadius);
            g2.dispose();

            super.paintComponent(g);
        }
    }

    private void initializeUI() {
        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ---------- Main Panel with gradient ----------
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 78, 132),
                                                     0, getHeight(), new Color(64, 142, 210));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // ---------- Header ----------
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(1000, 100));
        JLabel title = new JLabel("Learnify LMS - Admin Panel");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.setLayout(new BorderLayout());
        header.add(title, BorderLayout.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        // ---------- Content Panel ----------
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        // ---------- Cards ----------
        CardPanel usersCard = createCard("Managed Users", String.valueOf(admin.getManagedUsers().size()), new Color(10, 132, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1; gbc.weighty = 1;
        contentPanel.add(usersCard, gbc);

        CardPanel coursesCard = createCard("Managed Courses", String.valueOf(admin.getManagedCourses().size()), new Color(0, 178, 255));
        gbc.gridx = 1; gbc.gridy = 0;
        contentPanel.add(coursesCard, gbc);

        CardPanel levelCard = createCard("Admin Level", admin.getAdminLevel(), new Color(0, 215, 255));
        gbc.gridx = 2; gbc.gridy = 0;
        contentPanel.add(levelCard, gbc);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CardPanel loginCard = createCard("Last Login", sdf.format(admin.getLastLogin()), new Color(0, 178, 255));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; gbc.weighty = 0.5;
        contentPanel.add(loginCard, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // ---------- Bottom Panel ----------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(255, 70, 70));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutBtn.setPreferredSize(new Dimension(140, 45));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
        bottomPanel.add(logoutBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private CardPanel createCard(String title, String value, Color color) {
        CardPanel card = new CardPanel(color);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.setPreferredSize(new Dimension(300, 150));
        return card;
    }
}
