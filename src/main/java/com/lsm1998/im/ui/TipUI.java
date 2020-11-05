package com.lsm1998.im.ui;

import com.lsm1998.im.domain.User;
import com.lsm1998.im.utils.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class TipUI extends BaseUI
{
    public TipUI(User myInfo)
    {
        setUndecorated(true);
        getContentPane().setBackground(Color.PINK);
        String str = myInfo.getNickname() + "(" + myInfo.getUsername() + ")";
//        if (myInfo.getFlag() == 1)
//        {
//            str += "上线了";
//        } else
//        {
//            str += "下线了";
//        }
        JLabel myLabel = new JLabel(str, ImageUtil.getImageIconByPath(myInfo.getHeadImg()), JLabel.RIGHT);

        add(myLabel);
        setAlwaysOnTop(true);
        setSize(200, 100);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width - 200;
        int height = toolkit.getScreenSize().height;
        setVisible(true);
        for (int i = 0; i < 100; i++)
        {
            setLocation(width, height - i);
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        for (int i = 100; i > 0; i--)
        {
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        dispose();
    }

    @Override
    public String title()
    {
        return "";
    }
}
