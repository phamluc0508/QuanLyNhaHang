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
import com.test.app.view.booking.SearchFreeRoomFrm;
import com.test.app.view.room.ManageRoomFrm;

public class SellerHomeFrm extends JFrame implements ActionListener {
    private JButton btnBooking;
    private User user;

    public SellerHomeFrm(User user) {
        super("Seller home");
        this.user = user;

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        JPanel lblPane = new JPanel();
        lblPane.setLayout(new BoxLayout(lblPane, BoxLayout.LINE_AXIS));
        lblPane.add(Box.createRigidArea(new Dimension(450, 0)));
        JLabel lblUser = new JLabel("Loged in as: " + user.getName());
        lblUser.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblPane.add(lblUser);
        listPane.add(lblPane);
        listPane.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblHome = new JLabel("Seller's home");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(28.0f));
        listPane.add(lblHome);
        listPane.add(Box.createRigidArea(new Dimension(0, 20)));

        btnBooking = new JButton("Booking management");
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
        if ((e.getSource() instanceof JButton) &&
                (((JButton) e.getSource()).equals(btnBooking))) {
            (new SearchFreeRoomFrm(user)).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "This function is under construction!");
        }
    }
}