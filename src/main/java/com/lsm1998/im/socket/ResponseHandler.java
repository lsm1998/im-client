package com.lsm1998.im.socket;

import com.lsm1998.im.event.message.MessageEvent;
import com.lsm1998.im.utils.ContextAwareUtil;
import com.lsm1998.im.utils.GlobalUser;
import lombok.extern.slf4j.Slf4j;
import message.MessageOuterClass;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Slf4j
public class ResponseHandler extends Thread
{
    private static final byte[] EMPTY = new byte[0];
    private static final int MAX_LEN = 10 * 1024;

    private InputStream inputStream;
    private boolean online;

    public ResponseHandler(InputStream inputStream)
    {
        this.inputStream = inputStream;
        this.online = false;
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
            MessageOuterClass.MessageRequest request = MessageOuterClass.MessageRequest.parseFrom(bytes);
            if (request.getType() == MessageOuterClass.RequestType.Request && request.hasMessage())
            {
                handlerResponse(request.getMessage(), request.getCmd());
            } else if (request.getType() == MessageOuterClass.RequestType.Response && request.hasResponse())
            {
                handlerResponse(request.getResponse(), request.getCmd());
            } else
            {
                log.error("收到未知消息，message=" + request);
            }
        } catch (Exception e)
        {
            log.error("parse Message error,err={}", e.getMessage());
        }
    }

    private byte[] readBytes(InputStream inputStream)
    {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream())
        {
            int len;
            do
            {
                byte[] bytes = new byte[MAX_LEN];
                len = inputStream.read(bytes);
                bos.write(bytes, 0, len);
            } while (len == MAX_LEN);
            return bos.toByteArray();
        } catch (Exception e)
        {
            return EMPTY;
        }
    }

    public boolean isOnline()
    {
        return online;
    }

    private void handlerResponse(MessageOuterClass.Reply reply, MessageOuterClass.MessageType cmd)
    {
        if (reply.getCode() == 200)
        {
            this.online = true;
            switch (cmd)
            {
                case Handshake:
                    GlobalUser.setAesKey(reply.getBody().toStringUtf8());
                    break;
                case PrivateMessage:
                case Pong:
            }
        } else
        {
            JOptionPane.showMessageDialog(null, reply.getBody().toString(), "请求失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlerResponse(MessageOuterClass.Message message, MessageOuterClass.MessageType cmd)
    {
        switch (cmd)
        {
            case PrivateMessage:
                System.out.println("收到私聊消息");
                // appendView
                ContextAwareUtil.publishEvent(new MessageEvent(message));
                break;
            case SystemBroadcast:
                System.out.println("收到系统消息");
                break;
            case Online:
            case Offline:
            case GroupMessage:
                System.out.println("收到群发消息");
                break;
            case File:
                System.out.println("收到文件消息");
                break;
        }
        System.out.println("发送者ID=" + message.getFormId());
        System.out.println("内容=" + message.getBody().toStringUtf8());
    }
}
