package com.lsm1998.im.ui.component;

import com.lsm1998.im.domain.User;
import com.lsm1998.im.utils.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HeadImg extends DefaultListCellRenderer
{
    private List<User> userList;

    public HeadImg(List<User> userList)
    {
        this.userList = userList;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (index >= 0 && index < userList.size())
        {
            User user = userList.get(index);
            setIcon(ImageUtil.getImageIconByUrl(user.getHeadImg()));
            setText(user.getNickname() + "(" + user.getUsername() + ")");
        }

        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(false);
        return this;
    }
}
