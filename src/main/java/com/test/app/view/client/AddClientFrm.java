package com.test.app.view.client;

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
import com.test.app.dao.ClientDAO;
import com.test.app.model.Client;
import com.test.app.model.User;
import com.test.app.view.booking.ConfirmFrm;
import com.test.app.view.user.SellerHomeFrm;
import com.test.app.model.Booking;

public class AddClientFrm extends JFrame implements ActionListener {
    private final Client client;
    private final JTextField txtName;
    private final JTextField txtTel;
    private final JTextField txtAddress;
    private final JTextField txtEmail;
    private final JButton btnCreate;
    private final JButton btnCancel;
    private final User user;
    private final Booking booking;

    public AddClientFrm(User user, Booking booking) {
        super("Thêm mới khách hàng");
        this.user = user;
        this.booking = booking;
        this.client = new Client();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Thêm mới khách hàng");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        txtName = new JTextField(15);
        txtAddress = new JTextField(15);
        txtEmail = new JTextField(15);
        txtTel = new JTextField(15);
        btnCreate = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(7, 2));
        content.add(new JLabel("Tên:"));
        content.add(txtName);
        content.add(new JLabel("Địa chỉ:"));
        content.add(txtAddress);
        content.add(new JLabel("Số điện thoại:"));
        content.add(txtTel);
        content.add(new JLabel("Email:"));
        content.add(txtEmail);
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
            client.setName(txtName.getText());
            client.setAddress(txtAddress.getText());
            client.setTel(txtTel.getText());
            client.setEmail(txtEmail.getText());

            ClientDAO cd = new ClientDAO();
            Client c = cd.addClient(client);
            if (c != null) {
                JOptionPane.showMessageDialog(this,
                        "Thêm khách hàng thành công!");
                booking.setClient(c);
                new ConfirmFrm(user, booking).setVisible(true);
                this.dispose();
            }
        } else {
            new SellerHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}
