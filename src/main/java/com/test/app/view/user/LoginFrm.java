package com.test.app.view.user;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
import com.test.app.dao.UserDAO;
import com.test.app.model.User;
 
public class LoginFrm extends JFrame implements ActionListener{
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JButton btnLogin;
     
    public LoginFrm(){
        super("Đăng nhập");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Đăng nhập");
         
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Đăng nhập");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        JPanel pnUsername = new JPanel();
        pnUsername.setLayout(new FlowLayout());
        pnUsername.add(new JLabel("Tài Khoản:"));
        pnUsername.add(txtUsername);
        pnMain.add(pnUsername);
         
        JPanel pnPass = new JPanel();
        pnPass.setLayout(new FlowLayout());
        pnPass.add(new JLabel("Mật Khẩu:"));
        pnPass.add(txtPassword);
        pnMain.add(pnPass);

        pnMain.add(btnLogin);   
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
        btnLogin.addActionListener(this);   
         
        this.setSize(400,200);              
        this.setLocation(200,10);
        this.setContentPane(pnMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 
    public void actionPerformed(ActionEvent e) {
        if((e.getSource() instanceof JButton)
                &&(e.getSource().equals(btnLogin))) {
            User user = new User();
            user.setUsername(txtUsername.getText());
            user.setPassword(new String(txtPassword.getPassword()));
             
            UserDAO ud = new UserDAO();
            if(ud.checkLogin(user)) {
                if(user.getPosition().equalsIgnoreCase("quanly")) {
                    (new ManagerHomeFrm(user)).setVisible(true);
                    this.dispose();
                }else if(user.getPosition().equalsIgnoreCase("letan")) {
                    (new SellerHomeFrm(user)).setVisible(true);
                    this.dispose();
                }else
                    JOptionPane.showMessageDialog(this, 
                                 "The function of the role " + user.getPosition() 
                                 + " is under construction!");
            }else {
                JOptionPane.showMessageDialog(this, 
                          "Sai thông tin tài khoản hoặc mật khẩu!");
            }
        }
    }
}