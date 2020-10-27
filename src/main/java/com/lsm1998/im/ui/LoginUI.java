package com.lsm1998.im.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class LoginUI extends BaseUI
{


    public LoginUI()
    {
        this.setSize(800,500);
    }

    @Override
    public String title()
    {
        return "登录页面";
    }
}
