package com.msp.realestate;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User user;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public DashboardFrame(User user) {
        this.user = user;
        setTitle("MSP Construction - Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(41, 128, 185));
        topBar.setPreferredSize(new Dimension(1000, 60));
        JLabel title = new JLabel("MSP CONSTRUCTION & PROPERTIES");
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topBar.add(title, BorderLayout.WEST);

        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setPreferredSize(new Dimension(200, 500));
        sideMenu.setBackground(new Color(52, 73, 94));

        String[] menuItems = {"Dashboard", "Properties", "Documents", "Users"};

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new DashboardPanel(user), "Dashboard");
        contentPanel.add(new PropertiesPanel(user), "Properties");
        contentPanel.add(new DocumentsPanel(user), "Documents");
        contentPanel.add(new UsersPanel(), "Users");

        for (String item : menuItems) {
            if (item.equals("Users") && user.role.equals("GENERAL")) continue;
            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(e -> cardLayout.show(contentPanel, item));
            sideMenu.add(btn);
            sideMenu.add(Box.createVerticalStrut(6));
        }

        main.add(topBar, BorderLayout.NORTH);
        main.add(sideMenu, BorderLayout.WEST);
        main.add(contentPanel, BorderLayout.CENTER);

        add(main);
    }
}
