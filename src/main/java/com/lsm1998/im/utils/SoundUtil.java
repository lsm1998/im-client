package com.lsm1998.im.utils;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;

public class SoundUtil
{
    /**
     * 异步播放音乐
     *
     * @param fileName
     */
    public static void soundPlay(String fileName)
    {
        JobUtil.submit(() ->
        {
            String filePath = String.format("/static/sound/%s.mp3", fileName);
            Player player = null;
            try
            {
                player = new Player(new BufferedInputStream(FileUtil.class.getResourceAsStream(filePath)));
                player.play();
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                if (player != null)
                {
                    player.close();
                }
            }
        });
    }
}
