package com.lsm1998.im.event;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
{
    void onMessageEvent(E event);
}
