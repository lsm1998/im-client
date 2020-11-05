package com.lsm1998.im.event.message;

import com.lsm1998.im.event.ApplicationEvent;
import message.MessageOuterClass;

public class MessageEvent extends ApplicationEvent
{
    public MessageEvent(Object source)
    {
        super(source);
    }

    public MessageOuterClass.Message getMessage()
    {
        return (MessageOuterClass.Message) this.source;
    }
}
