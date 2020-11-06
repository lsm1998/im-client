package com.lsm1998.im.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class GlobalConfig
{
    @Value("${server.im.address}")
    private String imServerAddress;

    @Value("${server.im.port}")
    private Integer imServerPort;

    @Value("${server.file.address}")
    private String fileServerAddress;

    @Value("${server.file.port}")
    private Integer fileServerPort;

    @Value("${server.web.address}")
    private String webServerAddress;

    @Value("${server.web.port}")
    private Integer webServerPort;

    public String getImgBaseUrl()
    {
        return String.format("http://%s:%d/", fileServerAddress, fileServerPort);
    }

    public String getWebBaseUrl()
    {
        return String.format("http://%s:%d/", webServerAddress, webServerPort);
    }
}
