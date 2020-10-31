package com.lsm1998.im.ui.component;

import com.lsm1998.im.domain.User;

import javax.swing.*;
import java.util.List;

public class DataModel extends AbstractListModel<Object>
{
    List<User> data;

    public DataModel(List<User> data)
    {
        this.data = data;
    }

    public Object getElementAt(int index)
    {
        User info = data.get(index);
        return info.getNickname() + "(" + info.getUsername() + ")";
    }

    public int getSize()
    {
        return data.size();
    }
}
