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
import com.test.app.model.BookedTable;
import com.test.app.model.User;
import com.test.app.view.user.SellerHomeFrm;

public class ConfirmFrm extends JFrame implements ActionListener {
    private final JTable tblBookedTables;
    private final JTextField txtNote;
    private final JButton btnConfirm;
    private final JButton btnCancel;
    private final User user;
    private final Booking booking;
    private final BookingDAO bookingDAO;

    public ConfirmFrm(User user, Booking booking) {
        super("Xác nhận đặt bàn");
        this.user = user;
        this.booking = booking;
        this.bookingDAO = new BookingDAO();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Xác nhận đặt bàn");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        // Client information
        JPanel pnClient = new JPanel();
        pnClient.setLayout(new BoxLayout(pnClient, BoxLayout.Y_AXIS));
        pnClient.add(new JLabel("Thông tin khách hàng:"));
        pnClient.add(new JLabel("Tên: " + booking.getClient().getName()));
        pnClient.add(new JLabel("Số điện thoại: " + booking.getClient().getTel()));
        pnClient.add(new JLabel("Email: " + booking.getClient().getEmail()));
        pnMain.add(pnClient);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Booked tables table
        JPanel pnTables = new JPanel();
        pnTables.setLayout(new BoxLayout(pnTables, BoxLayout.Y_AXIS));
        pnTables.add(new JLabel("Bàn đã đặt:"));
        tblBookedTables = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblBookedTables);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 150));
        pnTables.add(scrollPane);
        pnMain.add(pnTables);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Note field
        JPanel pnNote = new JPanel();
        pnNote.setLayout(new BoxLayout(pnNote, BoxLayout.X_AXIS));
        pnNote.add(new JLabel("Ghi chú: "));
        txtNote = new JTextField();
        pnNote.add(txtNote);
        pnMain.add(pnNote);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new BoxLayout(pnButtons, BoxLayout.X_AXIS));
        btnConfirm = new JButton("Xác nhận");
        btnCancel = new JButton("Hủy");
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);
        pnButtons.add(btnConfirm);
        pnButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        pnButtons.add(btnCancel);
        pnMain.add(pnButtons);

        // Set up booked tables
        updateBookedTables();

        this.add(pnMain);
        this.setSize(600, 400);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void updateBookedTables() {
        ArrayList<BookedTable> bookedTables = booking.getBookedTables();
        String[] columnNames = {"Tên bàn", "Thời gian nhận", "Số lượng", "Ghi chú"};
        String[][] value = new String[bookedTables.size()][4];
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (int i = 0; i < bookedTables.size(); i++) {
            BookedTable br = bookedTables.get(i);
            value[i][0] = br.getTable().getName();
            value[i][1] = sdf.format(br.getCheckin());
            value[i][2] = br.getTable().getMaxNumber()+"";
            value[i][3] = br.getNote();
        }

        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBookedTables.setModel(tableModel);
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