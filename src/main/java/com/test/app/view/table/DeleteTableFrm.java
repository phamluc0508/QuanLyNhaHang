package com.test.app.view.table;
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
import com.test.app.dao.TableDAO;
import com.test.app.model.Table;
import com.test.app.model.User;
import com.test.app.view.user.ManagerHomeFrm;
 
public class DeleteTableFrm extends JFrame implements ActionListener{
    private final Table table;
    private final JTextField txtId;
    private final JTextField txtName;
    private final JTextField txtMaxNumber;
    private final JTextField txtDes;
    private final JButton btnDel;
    private final JButton btnCancel;
    private final User user;
     
     
    public DeleteTableFrm(User user, Table table){
        super("Delete a table");
        this.user = user;
        this.table = table;
         
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Delete a table");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        txtId = new JTextField(15);
        txtId.setEditable(false);
        txtName = new JTextField(15);
        txtName.setEditable(false);
        txtMaxNumber = new JTextField(15);
        txtMaxNumber.setEditable(false);
        txtDes = new JTextField(15);
        txtDes.setEditable(false);
        btnDel = new JButton("Delete");
        btnCancel = new JButton("Cancel");
         
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(6,2));
        content.add(new JLabel("Table ID:"));       content.add(txtId);
        content.add(new JLabel("Table name:"));     content.add(txtName);
        content.add(new JLabel("Max Number:"));     content.add(txtMaxNumber);
        content.add(new JLabel("Description:"));    content.add(txtDes);
        content.add(btnDel);                             content.add(btnCancel);
        pnMain.add(content);

        btnDel.addActionListener(this);
        btnCancel.addActionListener(this);
         
        initForm();     
        this.setContentPane(pnMain);
        this.setSize(600,300);              
        this.setLocation(200,10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
     
    private void initForm(){
        if(table != null){
            txtId.setText(table.getId()+"");
            txtName.setText(table.getName());
            txtMaxNumber.setText(table.getMaxNumber()+"");
            txtDes.setText(table.getDes());
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnCancel)){
            btnDelClick(false);
        }
        if(btnClicked.equals(btnDel)){
            btnDelClick(true);
        }
    }
     
    private void btnDelClick(Boolean isDelete){
        TableDAO rd = new TableDAO();
        if (isDelete) {
            try {
                if (rd.deleteTable(table)) {
                    JOptionPane.showMessageDialog(this,
                            "Xóa bàn thành công!");
                    new ManagerHomeFrm(user).setVisible(true);
                    this.dispose();
                }
            } catch (RuntimeException e){
                JOptionPane.showMessageDialog(this,
                        e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                new ManagerHomeFrm(user).setVisible(true);
                this.dispose();
            }
        } else {
            new ManagerHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}