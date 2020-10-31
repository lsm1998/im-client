package com.lsm1998.im.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lsm1998.im.domain.User;

public class JsonUtil
{
    private static final Gson gson = new Gson();

    public static User parseUser(String body)
    {
        try
        {
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            User user = new User();
            user.setToken(jsonObject.get("token").getAsString());

            JsonObject innerUser = jsonObject.get("user").getAsJsonObject();

            user.setId(innerUser.get("id").getAsLong());
            user.setNickname(innerUser.get("nickname").getAsString());
            user.setUsername(innerUser.get("username").getAsString());
            if(innerUser.has("aes_key"))
            {
                user.setAesKey(innerUser.get("aes_key").getAsString());
            }
            if(innerUser.has("head_img"))
            {
                user.setHeadImg(innerUser.get("head_img").getAsString());
            }
            return user;
        } catch (Exception e)
        {
            return null;
        }
    }
}
