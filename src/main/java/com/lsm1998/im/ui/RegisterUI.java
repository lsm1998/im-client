package com.lsm1998.im.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
@Scope(value = "prototype")
public class RegisterUI extends BaseUI
{
    private JButton cancelButton;
    private JButton registerButton;

    public RegisterUI()
    {
        this.setSize(500, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initLayout();
    }

    private void initLayout()
    {
        cancelButton = new JButton("返回");
        registerButton = new JButton("注册");
        registerButton.setBounds(150, 200, 60, 25);
        cancelButton.setBounds(280, 200, 60, 25);
        this.add(registerButton);
        this.add(cancelButton);
        cancelButton.addActionListener(this::cancelAction);
        registerButton.addActionListener(this::registerAction);
    }

    private void cancelAction(ActionEvent event)
    {
        this.jumpPage(this, LoginUI.class);
    }

    private void registerAction(ActionEvent event)
    {

    }

    @Override
    public String title()
    {
        return "注册页面";
    }
}
