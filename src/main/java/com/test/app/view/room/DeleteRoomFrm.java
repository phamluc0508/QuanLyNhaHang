package com.test.app.view.room;
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
 
public class DeleteRoomFrm extends JFrame implements ActionListener{
    private Room room;
    private JTextField txtId, txtName, txtType, txtPrice, txtDes;
    private JButton btnDel, btnCancel;
    private User user;
     
     
    public DeleteRoomFrm(User user, Room room){
        super("Delete a room");
        this.user = user;
        this.room = room;
         
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Edit a room");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        txtId = new JTextField(15);
        txtId.setEditable(false);
        txtName = new JTextField(15);
        txtName.setEditable(false);
        txtType = new JTextField(15);
        txtType.setEditable(false);
        txtPrice = new JTextField(15);
        txtPrice.setEditable(false);
        txtDes = new JTextField(15);
        txtDes.setEditable(false);
        btnDel = new JButton("Delete");
        btnCancel = new JButton("Cancel");
         
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(6,2));
        content.add(new JLabel("Room ID:"));    content.add(txtId);
        content.add(new JLabel("Room name:"));  content.add(txtName);
        content.add(new JLabel("Type:"));   content.add(txtType);
        content.add(new JLabel("Price:"));  content.add(txtPrice);
        content.add(new JLabel("Description:"));    content.add(txtDes);
        content.add(btnDel);     content.add(btnCancel);
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
        if(room != null){
            txtId.setText(room.getId()+"");
            txtName.setText(room.getName());
            txtType.setText(room.getType());
            txtPrice.setText(room.getPrice()+"");
            txtDes.setText(room.getDes());
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
        RoomDAO rd = new RoomDAO();
        if(isDelete){
            if(rd.deleteRoom(room)) {
                JOptionPane.showMessageDialog(this, 
                        "The room is succeffully deleted!");
                (new ManagerHomeFrm(user)).setVisible(true);
                this.dispose();
            }
        }
        else{
            (new ManagerHomeFrm(user)).setVisible(true);
            this.dispose();
        }
    }
}