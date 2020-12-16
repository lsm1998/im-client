package com.lsm1998.im.ui;

import com.lsm1998.im.utils.ContextAwareUtil;
import com.lsm1998.im.utils.ImageUtil;

import javax.swing.*;
import java.awt.*;

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

    /**
     * 设置本地图片
     *
     * @param path
     */
    public void setLocalIconImage(String path)
    {
        super.setIconImage(ImageUtil.getImageByPath(path));
    }

    /**
     * 设置网络图片
     *
     * @param url
     */
    public void setNetworkIconImage(String url)
    {
        super.setIconImage(ImageUtil.getImageByUrl(url));
    }
}
