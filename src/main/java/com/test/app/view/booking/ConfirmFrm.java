package com.test.app.view.booking;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import com.test.app.dao.BookingDAO;
import com.test.app.model.Booking;
import com.test.app.model.BookedRoom;
import com.test.app.model.User;
import com.test.app.view.user.SellerHomeFrm;

public class ConfirmFrm extends JFrame implements ActionListener {
    private JTable tblBookedRooms;
    private JTextField txtNote;
    private JButton btnConfirm;
    private JButton btnCancel;
    private User user;
    private Booking booking;
    private BookingDAO bookingDAO;

    public ConfirmFrm(User user, Booking booking) {
        super("Confirm booking");
        this.user = user;
        this.booking = booking;
        this.bookingDAO = new BookingDAO();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Confirm booking");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        // Client information
        JPanel pnClient = new JPanel();
        pnClient.setLayout(new BoxLayout(pnClient, BoxLayout.Y_AXIS));
        pnClient.add(new JLabel("Client Information:"));
        pnClient.add(new JLabel("Name: " + booking.getClient().getName()));
        pnClient.add(new JLabel("ID Card: " + booking.getClient().getIdCard()));
        pnClient.add(new JLabel("Tel: " + booking.getClient().getTel()));
        pnMain.add(pnClient);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Booked rooms table
        JPanel pnRooms = new JPanel();
        pnRooms.setLayout(new BoxLayout(pnRooms, BoxLayout.Y_AXIS));
        pnRooms.add(new JLabel("Booked Rooms:"));
        tblBookedRooms = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblBookedRooms);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 150));
        pnRooms.add(scrollPane);
        pnMain.add(pnRooms);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Note field
        JPanel pnNote = new JPanel();
        pnNote.setLayout(new BoxLayout(pnNote, BoxLayout.X_AXIS));
        pnNote.add(new JLabel("Note: "));
        txtNote = new JTextField();
        pnNote.add(txtNote);
        pnMain.add(pnNote);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new BoxLayout(pnButtons, BoxLayout.X_AXIS));
        btnConfirm = new JButton("Confirm");
        btnCancel = new JButton("Cancel");
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);
        pnButtons.add(btnConfirm);
        pnButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        pnButtons.add(btnCancel);
        pnMain.add(pnButtons);

        // Set up booked rooms table
        updateBookedRoomsTable();

        this.add(pnMain);
        this.setSize(600, 400);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void updateBookedRoomsTable() {
        ArrayList<BookedRoom> bookedRooms = booking.getBookedRooms();
        String[] columnNames = {"Room", "Check-in", "Check-out", "Price"};
        String[][] value = new String[bookedRooms.size()][4];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < bookedRooms.size(); i++) {
            BookedRoom br = bookedRooms.get(i);
            value[i][0] = br.getRoom().getName();
            value[i][1] = sdf.format(br.getCheckin());
            value[i][2] = sdf.format(br.getCheckout());
            value[i][3] = br.getPrice() + "";
        }

        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBookedRooms.setModel(tableModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnConfirm)) {
            booking.setNote(txtNote.getText().trim());
            booking.setUser(user);
            
            // Add booking to database
            boolean success = bookingDAO.addBooking(booking);
            if (success) {
                // Show success message and close
                JOptionPane.showMessageDialog(this, "Booking added successfully");
                (new SellerHomeFrm(user)).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add booking");
                // Show error message
            }
        } else if (btnClicked.equals(btnCancel)) {
            (new SellerHomeFrm(user)).setVisible(true);
            this.dispose();
        }
    }
} 