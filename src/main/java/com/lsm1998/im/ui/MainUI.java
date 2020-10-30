package com.lsm1998.im.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class MainUI extends BaseUI
{
    @Override
    public String title()
    {
        return "随便聊";
    }
}
