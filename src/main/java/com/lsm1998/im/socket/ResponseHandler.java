package com.lsm1998.im.socket;

import com.lsm1998.im.bean.GlobalUser;
import com.lsm1998.im.listener.ContextAwareUtil;
import lombok.extern.slf4j.Slf4j;
import message.MessageOuterClass;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Slf4j
public class ResponseHandler extends Thread
{
    private static final byte[] EMPTY = new byte[0];
    private static final int MAX_LEN = 10 * 1024;

    private InputStream inputStream;

    public ResponseHandler(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    @Override
    public void run()
    {
        while (true)
        {
            byte[] bytes = this.readBytes(inputStream);
            if (bytes == EMPTY)
            {
                break;
            }
            handler(bytes);
        }
    }

    private void handler(byte[] bytes)
    {
        try
        {
            MessageOuterClass.Message message = MessageOuterClass.Message.parseFrom(bytes);
            byte[] body = message.getBody().newInput().readAllBytes();
            switch (message.getCmd())
            {
                case Handshake:
                    GlobalUser.setAesKey(new String(body));
                    break;
                case File:
                    break;
                case SystemBroadcast:
                    System.out.println("收到广播消息:" + new String(body));
                    break;
                case PrivateMessage:
                    System.out.println("收到私聊消息:" + new String(body));
                    break;
            }
        } catch (Exception e)
        {
            log.error("parse Message error,err={}", e.getMessage());
        }
    }

    private byte[] readBytes(InputStream inputStream)
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            do
            {
                byte[] bytes = new byte[MAX_LEN];
                len = inputStream.read(bytes);
                bos.write(bytes, 0, len);
            } while (len < MAX_LEN);
            return bos.toByteArray();
        } catch (Exception e)
        {
            return EMPTY;
        }
    }
}
