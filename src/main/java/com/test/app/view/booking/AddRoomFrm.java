package com.test.app.view.booking;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.test.app.dao.RoomDAO;
import com.test.app.model.Room;
import com.test.app.model.User;
import com.test.app.view.user.ManagerHomeFrm;

public class AddRoomFrm extends JFrame implements ActionListener {
    private Room room;
    private JTextField txtName, txtType, txtPrice, txtDes;
    private JButton btnCreate, btnCancel;
    private User user;

    public AddRoomFrm(User user) {
        super("Add a room");
        this.user = user;
        this.room = new Room();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Edit a room");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        txtName = new JTextField(15);
        txtType = new JTextField(15);
        txtPrice = new JTextField(15);
        txtDes = new JTextField(15);
        btnCreate = new JButton("Create");
        btnCancel = new JButton("Cancel");

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(6, 2));
        content.add(new JLabel("Room name:"));
        content.add(txtName);
        content.add(new JLabel("Type:"));
        content.add(txtType);
        content.add(new JLabel("Price:"));
        content.add(txtPrice);
        content.add(new JLabel("Description:"));
        content.add(txtDes);
        content.add(btnCreate);
        content.add(btnCancel);
        pnMain.add(content);
        btnCreate.addActionListener(this);
        btnCancel.addActionListener(this);

        this.setContentPane(pnMain);
        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnCancel)) {
            btnCreateClick(false);
        }
        if (btnClicked.equals(btnCreate)) {
            btnCreateClick(true);
        }
    }

    private void btnCreateClick(Boolean isCreate) {
        if (isCreate) {
            room.setName(txtName.getText());
            room.setType(txtType.getText());
            room.setPrice(Float.parseFloat(txtPrice.getText()));
            room.setDes(txtDes.getText());

            RoomDAO rd = new RoomDAO();
            if (rd.createRoom(room)) {
                JOptionPane.showMessageDialog(this,
                        "The room is succeffully created!");
                (new ManagerHomeFrm(user)).setVisible(true);
                this.dispose();
            }
        } else {
            (new ManagerHomeFrm(user)).setVisible(true);
            this.dispose();
        }
    }
}