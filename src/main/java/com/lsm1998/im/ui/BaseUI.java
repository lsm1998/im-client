package com.lsm1998.im.ui;

import com.lsm1998.im.utils.ContextAwareUtil;

import javax.swing.*;

public abstract class BaseUI extends JFrame
{
    private static final int DEFAULT_H = 500;
    private static final int DEFAULT_W = 300;

    public BaseUI()
    {
        // 设置默认大小
        this.setSize(DEFAULT_H, DEFAULT_W);
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

    @Deprecated
    public <E> void setDate(E date)
    {

    }

    public abstract String title();

    /**
     * 跳转页面
     *
     * @param oldPage
     * @param newPage
     */
    public void jumpPage(BaseUI oldPage, Class<? extends BaseUI> newPage)
    {
        newPage(newPage);
        oldPage.dispose();
    }

    /**
     * 新建页面
     *
     * @param newPage
     */
    public void newPage(Class<? extends BaseUI> newPage)
    {
        ContextAwareUtil.getBean(newPage).showPage();
    }
}
