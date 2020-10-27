package com.lsm1998.im.ui;

import javax.swing.*;

public abstract class BaseUI extends JFrame
{
    private static final int DEFAULT_H = 500;
    private static final int DEFAULT_W = 300;

    public BaseUI()
    {
        // 设置默认大小
        this.setSize(DEFAULT_H, DEFAULT_W);
        // 退出窗口即退出
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 标题
        super.setTitle(this.title());
    }

    public void showPage()
    {
        this.setVisible(true);
    }

    public void close()
    {
        this.setVisible(false);
    }

    public void dispose()
    {
        this.close();
        super.dispose();
    }

    public void setSize(int width, int height)
    {
        super.setSize(width, height);
        this.setLocationRelativeTo(null);
    }

    public <E> void setDate(E date)
    {
    }

    public abstract String title();
}
