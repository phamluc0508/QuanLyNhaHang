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

public class AddTableFrm extends JFrame implements ActionListener {
    private final Table table;
    private final JTextField txtName;
    private final JTextField txtMaxNumber;
    private final JTextField txtDes;
    private final JButton btnCreate;
    private final JButton btnCancel;
    private final User user;

    public AddTableFrm(User user) {
        super("Thêm bài");
        this.user = user;
        this.table = new Table();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Thêm bàn");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        txtName = new JTextField(15);
        txtMaxNumber = new JTextField(15);
        txtDes = new JTextField(15);
        btnCreate = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(6, 2));
        content.add(new JLabel("Tên:"));
        content.add(txtName);
        content.add(new JLabel("Số lượng tối đa:"));
        content.add(txtMaxNumber);
        content.add(new JLabel("Mô tả:"));
        content.add(txtDes);
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
            table.setName(txtName.getText());
            table.setMaxNumber(Integer.parseInt(txtMaxNumber.getText()));
            table.setDes(txtDes.getText());

            TableDAO rd = new TableDAO();
            try {
                if (rd.createTable(table)) {
                    JOptionPane.showMessageDialog(this,
                            "Thêm bàn thành công!");
                    (new ManagerHomeFrm(user)).setVisible(true);
                    this.dispose();
                }
            } catch (RuntimeException e){
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        } else {
            (new ManagerHomeFrm(user)).setVisible(true);
            this.dispose();
        }
    }
}