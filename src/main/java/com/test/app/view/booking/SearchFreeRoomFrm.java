package com.test.app.view.booking;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;

import com.test.app.dao.RoomDAO;
import com.test.app.model.Booking;
import com.test.app.model.BookedRoom;
import com.test.app.model.Room;
import com.test.app.model.User;

public class SearchFreeRoomFrm extends JFrame implements ActionListener {
    private ArrayList<Room> listRoom;
    private JSpinner dateCheckin, dateCheckout;
    private JButton btnSearch;
    private JTable tblResult;
    private SearchFreeRoomFrm mainFrm;
    private User user;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SearchFreeRoomFrm(User user) {
        super("Search free room");
        this.user = user;
        mainFrm = this;
        listRoom = new ArrayList<Room>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Search free room");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1, BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width - 5, 20);
        pn1.add(new JLabel("Check-in date (yyyy-MM-dd): "));
        SpinnerDateModel dateModelCheckin = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        dateCheckin = new JSpinner(dateModelCheckin);
        JSpinner.DateEditor editorCheckin = new JSpinner.DateEditor(dateCheckin, "yyyy-MM-dd HH:mm");
        dateCheckin.setEditor(editorCheckin);
        pn1.add(dateCheckin);
        pn1.add(Box.createRigidArea(new Dimension(10, 0)));
        pn1.add(new JLabel("Check-out date (yyyy-MM-dd): "));
        SpinnerDateModel dateModelCheckout = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        dateCheckout = new JSpinner(dateModelCheckout);
        JSpinner.DateEditor editorCheckout = new JSpinner.DateEditor(dateCheckout, "yyyy-MM-dd HH:mm");
        dateCheckout.setEditor(editorCheckout);
        pn1.add(dateCheckout);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
        tblResult = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));

        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tblResult.getRowHeight();

                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    try {
                        Date checkin = (Date) dateCheckin.getValue();
                        Date checkout = (Date) dateCheckout.getValue();
                        Room selectedRoom = listRoom.get(row);
                        
                        // Create BookedRoom and add to Booking
                        BookedRoom br = new BookedRoom();
                        br.setRoom(selectedRoom);
                        br.setCheckin(checkin);
                        br.setCheckout(checkout);
                        br.setPrice(selectedRoom.getPrice());
                        br.setCheckedIn(false);
                        
                        Booking booking = new Booking();
                        booking.getBookedRooms().add(br);
                        booking.setUser(user);
                        booking.setBookDate(new Date());
                        
                        // Open SearchClientFrm
                        (new SearchClientFrm(user, booking)).setVisible(true);
                        mainFrm.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(mainFrm, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        pn2.add(scrollPane);
        pnMain.add(pn2);
        this.add(pnMain);
        this.setSize(800, 400);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnSearch)) {
            if ((dateCheckin.getValue() == null) || (dateCheckout.getValue() == null))
                return;
            try {
                Date checkin = (Date) dateCheckin.getValue();
                Date checkout = (Date) dateCheckout.getValue();
                
                RoomDAO rd = new RoomDAO();
                listRoom = rd.searchFreeRoom(checkin, checkout);

                String[] columnNames = {"Id", "Name", "Type", "Price", "Description"};
                String[][] value = new String[listRoom.size()][5];
                for (int i = 0; i < listRoom.size(); i++) {
                    value[i][0] = listRoom.get(i).getId() + "";
                    value[i][1] = listRoom.get(i).getName();
                    value[i][2] = listRoom.get(i).getType();
                    value[i][3] = listRoom.get(i).getPrice() + "";
                    value[i][4] = listRoom.get(i).getDes();
                }
                DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tblResult.setModel(tableModel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }
} 