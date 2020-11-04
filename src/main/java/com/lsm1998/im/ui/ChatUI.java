package com.lsm1998.im.ui;

import com.lsm1998.im.domain.User;
import com.lsm1998.im.utils.ImageUtil;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/10/3
 * 时间：12:21
 * 说明：
 */
public class ChatUI extends BaseUI implements ActionListener, ItemListener
{
    private JLabel title;
    JTextPane txtReceive, txtSend;
    private JButton btnSend, btnClose;
    private JButton btnShake, btnFile, btnColor, btnFace;
    private JComboBox<String> cbFont, cbSize;
    private User myInfo, friendInfo;
    private List<User> allList;
    private String sFont[] = {"宋体", "黑体", "楷体", "隶书"};
    private String sSize[] = {"8", "10", "12", "14", "16", "18", "24", "28", "32", "36", "72"};
    private Font font;
    private JTextArea jTextArea;

    public ChatUI(User myInfo, User friendInfo)
    {
        String str = myInfo.getNickname() + "(" + myInfo.getUsername() + ")和";
        str += friendInfo.getNickname() + "(" + friendInfo.getUsername() + ")正在聊天...";
        setIconImage(ImageUtil.getImageIconByUrl(myInfo.getHeadImg(), 75, 75).getImage());
        title = new JLabel(str, ImageUtil.getImageIconByUrl(myInfo.getHeadImg(), 75, 75), JLabel.LEFT);
        this.myInfo = myInfo;
        this.friendInfo = friendInfo;
        setTitle(str);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        JLabel titlebg = new JLabel(ImageUtil.getImageIconByPath("/static/images/2.jpg"));
        titlebg.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlebg.add(title);
        add(titlebg, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        txtReceive = new JTextPane();
        txtReceive.setEditable(false);
        centerPanel.add(new JScrollPane(txtReceive));
        JPanel sendPanel = new JPanel(new BorderLayout());
        JLabel btnPanel = new JLabel(ImageUtil.getImageIconByPath("/static/images/11.jpg"));
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        cbFont = new JComboBox<>(sFont);
        cbSize = new JComboBox<>(sSize);
        cbFont.addItemListener(this);
        cbSize.addItemListener(this);
        btnShake = new JButton(ImageUtil.getImageIconByPath("/static/images/zd.png"));
        //设置按钮的大小与图片一致
        btnShake.setMargin(new Insets(0, 0, 0, 0));
        btnFile = new JButton("文件");
        btnColor = new JButton("颜色");
        btnFace = new JButton(ImageUtil.getImageIconByPath("/static/images/sendFace.png"));
        btnFace.setMargin(new Insets(0, 0, 0, 0));
        // 群聊不允许发送抖动
        btnShake.addActionListener(this);
        btnFile.addActionListener(this);
        btnColor.addActionListener(this);
        btnFace.addActionListener(this);
        btnPanel.add(cbFont);
        btnPanel.add(cbSize);
        btnPanel.add(btnColor);

        btnPanel.add(btnShake);
        JButton yy = new JButton("语音");
        btnPanel.add(yy);
        yy.addActionListener(e ->
        {
            try
            {
//                    if (friendInfo.getFlag() != 1)
//                    {
//                        JOptionPane.showMessageDialog(null, "对方已经离线！");
//                        return;
//                    }
//                    SendMsg sendMsg = new SendMsg();
//                    sendMsg.myInfo = myInfo;
//                    sendMsg.friendInfo = friendInfo;
//                    sendMsg.cmd = Cmd.CMD_YY;
//                    SendCmd.send(sendMsg);
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        });
        btnPanel.add(btnFace);
        btnPanel.add(btnFile);
        sendPanel.add(btnPanel, BorderLayout.NORTH);
        txtSend = new JTextPane();
        sendPanel.add(txtSend, BorderLayout.CENTER);
        btnSend = new JButton("发送(S)");
        btnSend.setMnemonic('S');
        btnClose = new JButton("关闭(X)");
        btnClose.setMnemonic('X');
        btnSend.addActionListener(this);
        btnClose.addActionListener(this);
        JLabel bottombg = new JLabel(ImageUtil.getImageIconByPath("/static/images/11.jpg"));
        bottombg.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottombg.add(btnSend);
        bottombg.add(btnClose);
        sendPanel.add(bottombg, BorderLayout.SOUTH);
        centerPanel.add(new JScrollPane(sendPanel));
        add(centerPanel);
        JLabel lblboy = new JLabel(ImageUtil.getImageIconByPath("/static/images/6.jpg"));
        add(lblboy, BorderLayout.EAST);
        setSize(700, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //把发送框的内容提交到接收框，同时清除发送框的内容
    public void appendView(String name, StyledDocument xx) throws BadLocationException
    {
        //获取接收框的文档（内容）
        StyledDocument vdoc = txtReceive.getStyledDocument();
        // 格式化时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(date);
        SimpleAttributeSet as = new SimpleAttributeSet();
        String s = name + "    " + time + "\n";
//		saveRecord(name,s);//保存聊天记录
        vdoc.insertString(vdoc.getLength(), s, as);
        int end = 0;
        while (end < xx.getLength())
        {
            // 获得一个元素
            Element e0 = xx.getCharacterElement(end);
            //获取对应的风格
            SimpleAttributeSet as1 = new SimpleAttributeSet();
            StyleConstants.setForeground(as1, StyleConstants.getForeground(e0.getAttributes()));
            StyleConstants.setFontSize(as1, StyleConstants.getFontSize(e0.getAttributes()));
            StyleConstants.setFontFamily(as1, StyleConstants.getFontFamily(e0.getAttributes()));
            //获取该元素的内容
            s = e0.getDocument().getText(end, e0.getEndOffset() - end);
            // 将元素加到浏览窗中
            if ("icon".equals(e0.getName()))
            {
                vdoc.insertString(vdoc.getLength(), s, e0.getAttributes());
            } else
            {
                vdoc.insertString(vdoc.getLength(), s, as1);
//				saveRecord(name,s+"\n");//保存聊天记录
            }
            end = e0.getEndOffset();
        }
        // 输入一个换行
        vdoc.insertString(vdoc.getLength(), "\n", as);
        // 设置显示视图加字符的位置与文档结尾，以便视图滚动
        txtReceive.setCaretPosition(vdoc.getLength());
    }

    public void shake()
    {
        //抖动窗口
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        for (int i = 0; i < 20; i++)
        {
            if (i % 2 == 0)
            {
                this.setLocation(x + 2, y + 2);
            } else
            {
                this.setLocation(x - 2, y - 2);
            }
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnShake)
        {
            //发送消息
//            SendMsg msg = new SendMsg();
//            msg.cmd = Cmd.CMD_SHAKE;
//            msg.myInfo = myInfo;
//            msg.friendInfo = friendInfo;
//            SendCmd.send(msg);
            shake();
        } else if (e.getSource() == btnColor)
        {
            JColorChooser colordlg = new JColorChooser();
            Color color = colordlg.showDialog(this, "请选择字体颜色", Color.BLACK);
            txtSend.setForeground(color);
        } else if (e.getSource() == btnFace)
        {
            //打开表情窗口，选择表情图标
            int x, y;
            x = this.getLocation().x + 220;
            y = this.getLocation().y - 28;
            // new BqUI(this, x, y);
        } else if (e.getSource() == btnFile)
        {
            FileDialog dlg = new FileDialog(this, "请选择要发送的文件(64K以下)", FileDialog.LOAD);
            dlg.setVisible(true);
            String filename = dlg.getDirectory() + dlg.getFile();
            if (dlg.getDirectory() == null || dlg.getFile() == null)
            {
                return;
            }
            try
            {
                FileInputStream fis = new FileInputStream(filename);
                int size = fis.available();
                byte b[] = new byte[size];
                fis.read(b);
//                SendMsg msg = new SendMsg();
//                msg.cmd = Cmd.CMD_FILE;
//                msg.myInfo = myInfo;
//                msg.friendInfo = friendInfo;
//                msg.b = b;
//                msg.fileName = dlg.getFile();
//                SendCmd.send(msg);
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        } else if (e.getSource() == btnSend)
        {
            if (txtSend.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "请输入你要发送的内容。");
                return;
            }
            try
            {
                appendView(myInfo.getNickname(), txtSend.getStyledDocument());
                txtSend.setText("");
            } catch (BadLocationException e1)
            {
                e1.printStackTrace();
            }
        } else if (e.getSource() == btnClose)
        {
            dispose();
        }
    }

    public void setFont()
    {
        String sf = sFont[cbFont.getSelectedIndex()];
        String size = sSize[cbSize.getSelectedIndex()];
        font = new Font(sf, Font.PLAIN, Integer.parseInt(size));
        //设置发送文本框的字体
        txtSend.setFont(font);
    }

    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == cbFont)
        {
            setFont();
        } else if (e.getSource() == cbSize)
        {
            setFont();
        }
    }

    @Override
    public String title()
    {
        if (this.friendInfo == null)
        {
            return "";
        }
        return "和 " + this.friendInfo.getNickname() + " 的聊天";
    }
}
