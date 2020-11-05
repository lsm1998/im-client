package com.lsm1998.im.event;

import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject
{

    public ApplicationEvent(Object source)
    {
        super(source);
    }
}
