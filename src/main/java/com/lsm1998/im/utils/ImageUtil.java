package com.lsm1998.im.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class ImageUtil
{
    private static Toolkit toolKit = Toolkit.getDefaultToolkit();

    public static Image getImageByUrl(String url)
    {
        try
        {
            return toolKit.getImage(new URL(url));
        } catch (Exception e)
        {
            return null;
        }
    }

    public static ImageIcon getImageIconByPath(String path)
    {
        return getImageIconByStream(Objects.requireNonNull(FileUtil.getFile(path)));
    }

    public static ImageIcon getImageIconByStream(InputStream inputStream)
    {
        try
        {
            return new ImageIcon(inputStream.readAllBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImageByPath(String path)
    {
        return getImageByStream(Objects.requireNonNull(FileUtil.getFile(path)));
    }

    public static Image getImageByStream(InputStream inputStream)
    {
        try
        {
            return ImageIO.read(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
