package com.lsm1998.im.listener;

import com.lsm1998.im.ui.BaseUI;
import com.lsm1998.im.ui.LoginUI;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextAwareUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        ContextAwareUtil.applicationContext = applicationContext;
    }

    public static BaseUI getIndexPage()
    {
        return applicationContext.getBean(LoginUI.class);
    }
}
