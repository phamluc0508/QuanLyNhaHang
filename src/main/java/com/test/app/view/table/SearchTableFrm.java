package com.test.app.view.table;
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

import com.test.app.dao.TableDAO;
import com.test.app.model.Table;
import com.test.app.model.User;
 
public class SearchTableFrm extends JFrame implements ActionListener{
    private ArrayList<Table> listTable;
    private final JTextField txtKey;
    private final JButton btnSearch;
    private final JTable tblResult;
    private final SearchTableFrm mainFrm;
     
    public SearchTableFrm(User user, String title){
        super("Tìm kiếm bàn để " + title);
        mainFrm = this;
        listTable = new ArrayList<>();
         
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Tìm kiếm bàn để " + title);
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1,BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width-5, 20);
        pn1.add(new JLabel("Tên: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
 
        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2,BoxLayout.Y_AXIS));     
        tblResult = new JTable();
        JScrollPane scrollPane= new  JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false); 
        scrollPane.setPreferredSize(new
               Dimension(scrollPane.getPreferredSize().width, 250));
         
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().
                        getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY()/tblResult.getRowHeight(); // get row 
 
                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && 
                            column < tblResult.getColumnCount() && column >= 0) {
                    if(title.equals("sửa")){
                        (new EditTableFrm(user,
                                listTable.get(row))).setVisible(true);
                    }
                    else{
                        (new DeleteTableFrm(user,
                                listTable.get(row))).setVisible(true);
                    }
                    mainFrm.dispose();
                }
            }
        });
 
        pn2.add(scrollPane);
        pnMain.add(pn2);    
        this.add(pnMain);
        this.setSize(600,300);              
        this.setLocation(200,10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnSearch)){
            if((txtKey.getText() == null)||(txtKey.getText().isEmpty()))
                return;
            TableDAO rd = new TableDAO();
            listTable = rd.searchTable(txtKey.getText().trim());
 
            String[] columnNames = {"Id", "Tên", "Số lượng tối đa", "Mô tả"};
            String[][] value = new String[listTable.size()][5];
            for(int i=0; i<listTable.size(); i++){
                value[i][0] = listTable.get(i).getId() +"";
                value[i][1] = listTable.get(i).getName();
                value[i][2] = listTable.get(i).getMaxNumber() +"";
                value[i][3] = listTable.get(i).getDes();
            }
            DefaultTableModel tableModel = 
                       new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                   //unable to edit cells
                   return false;
                }
            };
            tblResult.setModel(tableModel);
        }
    }
}