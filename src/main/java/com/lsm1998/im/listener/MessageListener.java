package com.lsm1998.im.listener;

import com.lsm1998.im.event.ApplicationListener;
import com.lsm1998.im.event.message.MessageEvent;
import com.lsm1998.im.utils.GlobalUtil;
import message.MessageOuterClass;
import org.springframework.stereotype.Service;

import com.lsm1998.im.ui.MainUI;

@Service
public class MessageListener implements ApplicationListener<MessageEvent>
{
    @Override
    public void onMessageEvent(MessageEvent event)
    {
        MessageOuterClass.Message message = event.getMessage();
        MainUI main = GlobalUtil.get(MainUI.GLOBAL_KEY);
        if (main == null)
        {
            return;
        }
        main.appendView("demo", message.getBody().toStringUtf8());
    }
}
