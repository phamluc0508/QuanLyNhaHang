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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

import com.test.app.dao.TableDAO;
import com.test.app.model.Booking;
import com.test.app.model.BookedTable;
import com.test.app.model.Table;
import com.test.app.model.User;
import com.test.app.view.client.SearchClientFrm;

public class SearchFreeTableFrm extends JFrame implements ActionListener {
    private ArrayList<Table> listTable;
    private final JSpinner dateCheckin;
    private final JTextField maxNumberExpect;
    private final JButton btnSearch;
    private final JTable tblResult;
    private final SearchFreeTableFrm mainFrm;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SearchFreeTableFrm(User user) {
        super("Tìm kiếm bàn trống");
        mainFrm = this;
        listTable = new ArrayList<>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Tìm kiếm bàn trống");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1, BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width - 5, 20);
        pn1.add(new JLabel("Thời gian nhận bàn (yyyy-MM-dd HH:mm): "));
        SpinnerDateModel dateModelCheckin = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        dateCheckin = new JSpinner(dateModelCheckin);
        JSpinner.DateEditor editorCheckin = new JSpinner.DateEditor(dateCheckin, "yyyy-MM-dd HH:mm");
        dateCheckin.setEditor(editorCheckin);
        pn1.add(dateCheckin);
        pn1.add(Box.createRigidArea(new Dimension(10, 0)));
        pn1.add(new JLabel("Số lượng: "));
        maxNumberExpect = new JTextField();
        pn1.add(maxNumberExpect);
        btnSearch = new JButton("Tìm kiếm");
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
                        int maxNumber = Integer.parseInt(maxNumberExpect.getText());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(checkin);
                        if(maxNumber <= 2){
                            calendar.add(Calendar.HOUR_OF_DAY, 2);
                        } else if (maxNumber <= 6){
                            calendar.add(Calendar.HOUR_OF_DAY, 3);
                        } else {
                            calendar.add(Calendar.HOUR_OF_DAY, 4);
                        }
                        Date checkout = calendar.getTime();
                        Table selectedTable = listTable.get(row);
                        
                        // Create BookedTable and add to Booking
                        BookedTable br = new BookedTable();
                        br.setTable(selectedTable);
                        br.setCheckin(checkin);
                        br.setCheckout(checkout);
                        br.setPrice(0);
                        br.setCheckedIn(false);
                        
                        Booking booking = new Booking();
                        booking.getBookedTables().add(br);
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
            if ((dateCheckin.getValue() == null) || (maxNumberExpect.getText() == null))
                return;
            try {
                Date checkin = (Date) dateCheckin.getValue();
                int maxNumber = Integer.parseInt(maxNumberExpect.getText());
                
                TableDAO rd = new TableDAO();
                listTable = rd.searchFreeTable(checkin, maxNumber);

                String[] columnNames = {"Id", "Tên", "Số lượng tối đa", "Mô tả"};
                String[][] value = new String[listTable.size()][5];
                for (int i = 0; i < listTable.size(); i++) {
                    value[i][0] = listTable.get(i).getId() + "";
                    value[i][1] = listTable.get(i).getName();
                    value[i][2] = listTable.get(i).getMaxNumber() + "";
                    value[i][3] = listTable.get(i).getDes();
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