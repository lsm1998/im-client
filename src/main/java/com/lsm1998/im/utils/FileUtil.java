package com.lsm1998.im.utils;

import java.io.InputStream;

public class FileUtil
{
    public static InputStream getFile(String filePath)
    {
        return FileUtil.class.getResourceAsStream(filePath);
    }
}
