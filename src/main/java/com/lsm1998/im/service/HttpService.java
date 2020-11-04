package com.lsm1998.im.service;

import com.lsm1998.im.config.GlobalConfig;
import com.lsm1998.im.domain.Friends;
import com.lsm1998.im.domain.User;
import com.lsm1998.im.socket.TcpClient;
import com.lsm1998.im.utils.GlobalUser;
import com.lsm1998.im.utils.HTTPClientUtil;
import com.lsm1998.im.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lsm1998.im.utils.HTTPClientUtil.HTTP_OK;

@Service
@Slf4j
public class HttpService
{
    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private TcpClient client;

    public boolean login(String username, String password)
    {
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\",\"rememberMe\":true}", username, password);
        try
        {
            HttpResponse<String> response = HTTPClientUtil.post(globalConfig.getWebBaseUrl() + "base/login", json, null);
            if (response.statusCode() == HTTP_OK)
            {
                log.info("登录成功,body={}", response.body());
                User user = JsonUtil.parseUser(response.body());
                if (user == null)
                {
                    log.error("解析user失败");
                    return false;
                }
                // 设置当前用户
                GlobalUser.setUser(user);
                // 发送握手请求
                client.sendHandshake(user.getToken());
                return true;
            }
            return false;
        } catch (Exception e)
        {
            log.error("登录请求失败,err={}", e.getMessage());
            return false;
        }
    }

    public List<Friends> findGroupList()
    {
        User user = GlobalUser.getUser();
        if (user == null)
        {
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("uid", String.format("%d", user.getId()));
        headers.put("token", user.getToken());
        try
        {
            HttpResponse<String> response = HTTPClientUtil.get(globalConfig.getWebBaseUrl() + "user/friendsList", headers);
            return JsonUtil.parseGroupList(response.body());
        } catch (Exception e)
        {
            log.error("获取好友分组失败");
            e.printStackTrace();
            return null;
        }
    }
}
