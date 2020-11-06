package com.lsm1998.im.listener;

import com.lsm1998.im.ui.BaseUI;
import com.lsm1998.im.utils.ContextAwareUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class IndexPageListener implements ApplicationListener<ContextRefreshedEvent>
{
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (event.getApplicationContext().getParent() == null)
        {
            try
            {
                // 加载Style
                Class.forName("com.lsm1998.im.utils.StyleUtil");
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            BaseUI indexPage = ContextAwareUtil.getIndexPage();
            indexPage.showPage();
        }
    }
}
