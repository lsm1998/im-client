package com.lsm1998.im.utils;

import java.io.InputStream;

public class FileUtil
{
    public static InputStream getFile(String filePath)
    {
        // ClassPathResource classPathResource = new ClassPathResource(filePath);
        // return classPathResource.getInputStream();
        // ResourceUtils.getFile("classpath:sql/initData.sql");
        return FileUtil.class.getResourceAsStream(filePath);
    }
}
