package com.lsm1998.im.ui;

import com.lsm1998.im.utils.ContextAwareUtil;
import com.lsm1998.im.service.HttpService;
import com.lsm1998.im.utils.ImageUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
@Scope(value = "prototype")
public class LoginUI extends BaseUI
{
    private final HttpService httpService;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI()
    {
        setIconImage(ImageUtil.getImageByPath("/static/images/icon.png"));
        this.setSize(430, 330);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.httpService = ContextAwareUtil.getBean(HttpService.class);
        this.initLayout();
    }

    private void initLayout()
    {
        JLabel backgroundLabel = new JLabel(ImageUtil.getImageIconByPath("/static/images/login.png"));
        backgroundLabel.setLayout(null);
        JLabel usernameLabel = new JLabel("账号");
        JLabel passwordLabel = new JLabel("密码");
        usernameField = new JTextField(100);
        passwordField = new JPasswordField(100);
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        usernameLabel.setBounds(100, 100, 100, 35);
        usernameField.setBounds(180, 100, 180, 35);
        passwordLabel.setBounds(100, 180, 100, 35);
        passwordField.setBounds(180, 180, 180, 35);
        loginButton.setBounds(150, 260, 60, 25);
        registerButton.setBounds(230, 260, 60, 25);
        backgroundLabel.add(usernameLabel);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(usernameField);
        backgroundLabel.add(passwordField);
        backgroundLabel.add(loginButton);
        backgroundLabel.add(registerButton);
        this.add(backgroundLabel);
        loginButton.addActionListener(this::loginAction);
        registerButton.addActionListener(this::registerAction);
    }

    private void registerAction(ActionEvent event)
    {
        this.jumpPage(this, RegisterUI.class);
    }

    private void loginAction(ActionEvent event)
    {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        if (username.length() == 0 || password.length == 0)
        {
            JOptionPane.showMessageDialog(null, "请完善账号或密码", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.httpService.login(username, new String(password)))
        {
            this.jumpPage(this, MainUI.class);
        } else
        {
            JOptionPane.showMessageDialog(null, "登录失败", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String title()
    {
        return "登录页面";
    }
}
