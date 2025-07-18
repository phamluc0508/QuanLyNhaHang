package com.test.app.view.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.test.app.model.User;
import com.test.app.view.booking.SearchFreeTableFrm;
import com.test.app.view.table.ManageTableFrm;

public class SellerHomeFrm extends JFrame implements ActionListener {
    private final JButton btnBooking;
    private final JButton btnLogOut;
    private final User user;

    public SellerHomeFrm(User user) {
        super("Lễ tân");
        this.user = user;

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        btnLogOut = new JButton("Đăng xuất");
        btnLogOut.addActionListener(this);

        JLabel lblUser = new JLabel("Lễ tân: " + user.getName());

        JPanel lblPane = new JPanel();
        lblPane.setLayout(new BoxLayout(lblPane, BoxLayout.LINE_AXIS));
        lblPane.add(Box.createHorizontalGlue());
        lblPane.add(lblUser);
        lblPane.add(Box.createRigidArea(new Dimension(10, 0)));
        lblPane.add(btnLogOut);
        listPane.add(lblPane);
        listPane.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblHome = new JLabel("Lễ tân");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(28.0f));
        listPane.add(lblHome);
        listPane.add(Box.createRigidArea(new Dimension(0, 20)));

        btnBooking = new JButton("Đặt bàn");
        btnBooking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBooking.addActionListener(this);
        listPane.add(btnBooking);

        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.add(listPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object source = e.getSource();
        if (source == btnBooking) {
            new SearchFreeTableFrm(user).setVisible(true);
            this.dispose();
        }
        else if (source == btnLogOut) {
            new LoginFrm().setVisible(true);
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "This function is under construction!");
        }
    }
}