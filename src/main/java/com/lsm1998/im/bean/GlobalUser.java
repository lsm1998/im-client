package com.lsm1998.im.bean;

import com.lsm1998.im.domain.User;

public class GlobalUser
{
    private static User user = null;

    public static void setUser(User user)
    {
        GlobalUser.user = user;
    }

    public static User getUser()
    {
        return user;
    }

    public static void setAesKey(String aesKey)
    {
        if (user != null)
        {
            user.setAesKey(aesKey);
        }
    }

    public static String getAesKey()
    {
        if (user != null)
        {
            return user.getAesKey();
        }
        return null;
    }
}
