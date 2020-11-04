package com.lsm1998.im.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lsm1998.im.domain.Friends;
import com.lsm1998.im.domain.User;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil
{
    public static User parseUser(String body)
    {
        try
        {
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            User user = User.builder().build();
            user.setToken(jsonObject.get("token").getAsString());
            JsonObject innerUser = jsonObject.get("user").getAsJsonObject();
            user.setId(innerUser.get("id").getAsLong());
            user.setNickname(innerUser.get("nickname").getAsString());
            user.setUsername(innerUser.get("username").getAsString());
            if (innerUser.has("aes_key"))
            {
                user.setAesKey(innerUser.get("aes_key").getAsString());
            }
            if (innerUser.has("head_img"))
            {
                user.setHeadImg(innerUser.get("head_img").getAsString());
            }
            return user;
        } catch (Exception e)
        {
            return null;
        }
    }

    public static List<Friends> parseGroupList(String body)
    {
        List<Friends> result = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        JsonArray friendsList = jsonObject.get("list").getAsJsonArray();
        friendsList.forEach(e ->
        {
            JsonObject object = e.getAsJsonObject();
            Friends f = Friends.builder()
                    .groupId(object.get("group_id").getAsLong())
                    .userList(new ArrayList<>())
                    .build();
            JsonArray userList = object.get("list").getAsJsonArray();
            userList.forEach(u ->
            {
                JsonObject objectUser = u.getAsJsonObject();
                User build = User.builder()
                        .id(objectUser.get("id").getAsLong())
                        .nickname(objectUser.get("nickname").getAsString())
                        .username(objectUser.get("username").getAsString())
                        .build();
                if (objectUser.has("head_img"))
                {
                    build.setHeadImg(objectUser.get("head_img").getAsString());
                }
                f.getUserList().add(build);
            });
            result.add(f);
        });
        return result;
    }
}
