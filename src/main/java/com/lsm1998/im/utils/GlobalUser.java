package com.lsm1998.im.utils;

import com.lsm1998.im.domain.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalUser
{
    private static User currentUser = null;

    public static void setUser(User user)
    {
        GlobalUser.currentUser = user;
    }

    public static User getUser()
    {
        return currentUser;
    }

    public static void setAesKey(String aesKey)
    {
        if (currentUser != null)
        {
            currentUser.setAesKey(aesKey);
            log.info("设置aesKey={}", aesKey);
        }
    }

    public static String getAesKey()
    {
        if (currentUser != null)
        {
            return currentUser.getAesKey();
        }
        return null;
    }

    public static void setToken(String token)
    {
        if (currentUser != null)
        {
            currentUser.setToken(token);
        }
    }

    public static String getToken()
    {
        if (currentUser != null)
        {
            return currentUser.getToken();
        }
        return null;
    }
}
