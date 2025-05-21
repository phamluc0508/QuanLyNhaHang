package com.test.app.view.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.test.app.dao.ClientDAO;
import com.test.app.model.Booking;
import com.test.app.model.Client;
import com.test.app.model.User;
import com.test.app.view.booking.ConfirmFrm;

public class SearchClientFrm extends JFrame implements ActionListener {
    private ArrayList<Client> listClient;
    private final JTextField txtKey;
    private final JButton btnSearch;
    private final JButton btnAdd;
    private final JTable tblResult;
    private final SearchClientFrm mainFrm;
    private final User user;
    private final Booking booking;

    public SearchClientFrm(User user, Booking booking) {
        super("Tìm kiếm khách hàng");
        this.user = user;
        this.booking = booking;
        mainFrm = this;
        listClient = new ArrayList<>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Tìm kiếm khách hàng");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1, BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width - 5, 20);
        pn1.add(new JLabel("Tên: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(this);
        pn1.add(btnAdd);
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
                    Client selectedClient = listClient.get(row);
                    booking.setClient(selectedClient);
                    
                    // Open ConfirmFrm
                    (new ConfirmFrm(user, booking)).setVisible(true);
                    mainFrm.dispose();
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
            ClientDAO cd = new ClientDAO();
            listClient = cd.searchClient(txtKey.getText().trim());

            String[] columnNames = {"Id", "Tên", "Địa chỉ", "Số điện thoại", "Email"};
            String[][] value = new String[listClient.size()][5];
            for (int i = 0; i < listClient.size(); i++) {
                value[i][0] = listClient.get(i).getId() + "";
                value[i][1] = listClient.get(i).getName();
                value[i][2] = listClient.get(i).getAddress();
                value[i][3] = listClient.get(i).getTel();
                value[i][4] = listClient.get(i).getEmail();
            }
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblResult.setModel(tableModel);
        } else if (btnClicked.equals(btnAdd)) {
            (new AddClientFrm(user, booking)).setVisible(true);
            mainFrm.dispose();
        }
    }
} 