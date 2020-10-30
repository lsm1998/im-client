package com.lsm1998.im.service;

import com.lsm1998.im.config.GlobalConfig;
import com.lsm1998.im.utils.HTTPClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

import static com.lsm1998.im.utils.HTTPClientUtil.HTTP_OK;

@Service
@Slf4j
public class HttpService
{
    @Autowired
    private GlobalConfig globalConfig;

    public boolean login(String username, String password)
    {
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\",\"rememberMe\":true}", username, password);
        try
        {
            HttpResponse<String> response = HTTPClientUtil.post(globalConfig.getWebBaseUrl() + "/base/login", json, null);
            if (response.statusCode() == HTTP_OK)
            {
                response.headers().map().forEach((k, v) ->
                {
//                    if (AUTH_KEY.equals(k) && v.size() > 0)
//                    {
//                        token = v.get(0);
//                    }
                });
                return true;
            }
            return false;
        } catch (Exception e)
        {
            log.error("登录请求失败,err={}", e.getMessage());
            return false;
        }
    }
}
