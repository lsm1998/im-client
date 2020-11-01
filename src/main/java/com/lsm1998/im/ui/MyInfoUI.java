package com.lsm1998.im.ui;

import com.lsm1998.im.domain.User;
import com.lsm1998.im.utils.GlobalUser;
import com.lsm1998.im.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Component
@Scope(value = "prototype")
@Slf4j
public class MyInfoUI extends BaseUI
{
    private User user;

    private JComboBox<Character> textSex;

    private JComboBox<Object> textD;
    private JComboBox<Object> textDay;
    private JComboBox<Object> textM;

    private JTextArea textArea;

    private JTextField textName;

    private JPasswordField passwordField;

    public MyInfoUI()
    {
        this.user = GlobalUser.getUser();
        if (this.user == null)
        {
            log.error("用户未登录");
            this.dispose();
            return;
        }
        this.setSize(330, 550);
        this.initLayout();
    }

    private void initLayout()
    {
        JLabel zc = new JLabel(ImageUtil.getImageIconByUrl("/static/images/9.png"));
        zc.setLayout(null);

        JLabel labName = new JLabel("昵  称  ");
        labName.setFont(new Font("幼圆", Font.PLAIN, 12));
        labName.setBounds(20, 5, 100, 30);
        textName = new JTextField(20);
        textName.setBounds(80, 5, 110, 30);
        zc.add(labName);
        textName.setText(user.getNickname());
        zc.add(textName);

        JLabel labSex = new JLabel("性  别  ");
        labSex.setFont(new Font("幼圆", Font.PLAIN, 12));
        labSex.setBounds(20, 45, 100, 30);
        textSex = new JComboBox<>();
        if (user.getSex() == '女')
        {
            textSex.addItem('女');
            textSex.addItem('男');
        } else
        {
            textSex.addItem('男');
            textSex.addItem('女');
        }
        textSex.setFont(new Font("幼圆", Font.PLAIN, 12));
        textSex.setBounds(80, 45, 110, 30);
        zc.add(labSex);
        zc.add(textSex);

        JLabel labDay = new JLabel("生  日  ");
        labDay.setFont(new Font("幼圆", Font.PLAIN, 12));
        labDay.setBounds(20, 85, 100, 30);
        zc.add(labDay);

        Object[] arr1 = new Object[101];
        for (int i = 1; i < 100; i++)
        {
            arr1[i] = i + 1950;
        }
        arr1[0] = "无";
        textDay = new JComboBox<>(arr1);
        textDay.setFont(new Font("幼圆", Font.PLAIN, 12));
        textDay.setBounds(80, 85, 60, 30);
        zc.add(textDay);
        Object[] arr2 = new Object[13];
        for (int i = 1; i < 13; i++)
        {
            arr2[i] = i;
        }
        arr2[0] = "无";
        textM = new JComboBox<>(arr2);
        textM.setFont(new Font("幼圆", Font.PLAIN, 12));
        textM.setBounds(145, 85, 40, 30);
        zc.add(textM);
        Object[] arr3 = new Object[32];
        for (int i = 1; i < 32; i++)
        {
            arr3[i] = i;
        }
        arr3[0] = "无";
        textD = new JComboBox<>(arr3);

        textD.setFont(new Font("幼圆", Font.PLAIN, 12));
        textD.setBounds(185, 85, 40, 30);
        zc.add(textD);
        zc.setBounds(300, 500, 200, 80);
        JLabel labgxqm = new JLabel("个 性 签 名");
        labgxqm.setFont(new Font("幼圆", Font.PLAIN, 12));
        textArea = new JTextArea(10, 50);
        if (user.getSignature() != null)
        {
            textArea.setText(user.getSignature());
        }
        textArea.setFont(new Font("幼圆", Font.PLAIN, 12));
        labgxqm.setBounds(20, 125, 100, 30);
        textArea.setBounds(101, 125, 180, 90);
        zc.add(labgxqm);
        zc.add(textArea);
        String str = user.getBirthDay();
        if (str != null && str.split("-").length == 3)
        {
            int i1 = Integer.parseInt(str.split("-")[0]);
            int i2 = Integer.parseInt(str.split("-")[1]);
            int i3 = Integer.parseInt(str.split("-")[2]);
            textDay.setSelectedIndex(i1 - 1950);
            textM.setSelectedIndex(i2);
            textD.setSelectedIndex(i3);
        }
        JLabel labtx = new JLabel("修 改 头 像");
        labtx.setFont(new Font("幼圆", Font.PLAIN, 12));
        labtx.setBounds(20, 245, 100, 30);
        zc.add(labtx);
        ImageIcon icon1 = ImageUtil.getImageIconByUrl(user.getHeadImg(),60,60);
        JButton tx1 = new JButton(icon1);
        tx1.setIcon(icon1);
        tx1.setBounds(101, 265, 60, 60);
        zc.add(tx1);
        JLabel passlbl = new JLabel("密码");
        passwordField = new JPasswordField();

        passlbl.setBounds(31, 370, 50, 30);
        passwordField.setBounds(121, 370, 100, 30);
        passwordField.setText(user.getPassword());
        zc.add(passlbl);
        zc.add(passwordField);

        JButton tx2 = new JButton("上传");
        tx2.addActionListener(e ->
        {
            JFileChooser dlg = new JFileChooser();
            dlg.setDialogType(JFileChooser.OPEN_DIALOG);
            dlg.setFileSelectionMode(JFileChooser.FILES_ONLY);
            dlg.setDialogTitle("更换皮肤");
            if (dlg.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                File file = dlg.getSelectedFile();
                String path = file.getPath();
                int index = path.lastIndexOf(".");
                String type = path.substring(index);
                switch (type)
                {
                    case ".jpg":
                    case ".png":
                    case ".jpeg":
//                        ImgUtil.changeImg(path, "temp" + type, 60);
//                        File file1 = new File("temp" + type);
//                        try (FileInputStream fis = new FileInputStream(file1))
//                        {
//                            byte[] b = new byte[(int) file1.length()];
//                            fis.read(b);
//                            user.setHead_img(b);
//                            tx1.setIcon(new ImageIcon(user.getHead_img()));
//                            tx1.repaint();
//                        } catch (Exception e1)
//                        {
//                            e1.printStackTrace();
//                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "只支持jpg、png、jpeg类型");
                        break;
                }
            }
        });
        tx2.setBounds(170, 265, 60, 30);
        zc.add(tx2);

        JButton bc = new JButton("保存");
        bc.setFont(new Font("幼圆", Font.PLAIN, 12));
        bc.setBackground(Color.PINK);
        bc.setForeground(Color.WHITE);
        bc.setBounds(31, 445, 100, 30);
        zc.add(bc);
        // bc.addActionListener(e -> save());
        JButton gb = new JButton("关闭");
        gb.addActionListener(e -> this.dispose());
        gb.setFont(new Font("幼圆", Font.PLAIN, 12));
        gb.setBackground(Color.PINK);
        gb.setForeground(Color.WHITE);
        gb.setBounds(171, 445, 100, 30);
        zc.add(gb);
        this.add(zc);
    }

    @Override
    public String title()
    {
        return "我的信息";
    }
}
