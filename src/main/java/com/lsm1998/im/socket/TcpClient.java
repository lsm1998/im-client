package com.lsm1998.im.socket;

import com.google.protobuf.ByteString;
import com.lsm1998.im.config.GlobalConfig;
import com.lsm1998.im.utils.GlobalUser;
import com.lsm1998.im.domain.User;
import lombok.extern.slf4j.Slf4j;
import message.MessageOuterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Service
@Slf4j
public class TcpClient
{
    @Autowired
    private GlobalConfig globalConfig;

    private ResponseHandler responseHandler;

    private OutputStream outputStream;

    private boolean connect;

    @PostConstruct
    public void init()
    {
        try
        {
            Socket socket = new Socket(globalConfig.getImServerAddress(), globalConfig.getImServerPort());
            this.outputStream = socket.getOutputStream();
            this.responseHandler = new ResponseHandler(socket.getInputStream());
            this.responseHandler.start();
            log.info("连接完成");
            this.connect = true;
        } catch (IOException e)
        {
            this.connect = false;
            log.error("连接完成失败,err={}", e.getMessage());
        }
    }

    private void checkConnect()
    {
        if (!this.connect)
        {
            log.error("离线状态发送数据失败");
            throw new RuntimeException("离线状态发送数据失败");
        }
    }

    public void sendPong() throws IOException
    {
        User user = GlobalUser.getUser();
        MessageOuterClass.Message message = MessageOuterClass.Message.newBuilder()
                .setFormId(user.getId()).build();
        this.send(message, MessageOuterClass.MessageType.Pong);
    }

    /**
     * 发送握手信息
     *
     * @param token
     * @throws IOException
     */
    public void sendHandshake(String token) throws IOException
    {
        User user = GlobalUser.getUser();
        MessageOuterClass.Message message = MessageOuterClass.Message.newBuilder()
                .setLength(token.getBytes().length)
                .setFormId(user.getId())
                .setBody(ByteString.copyFrom(token.getBytes())).build();
        this.send(message, MessageOuterClass.MessageType.Handshake);
    }

    public void sendPrivateMessage(long toId, String body) throws IOException
    {
        User user = GlobalUser.getUser();
        MessageOuterClass.Message message = MessageOuterClass.Message.newBuilder()
                .setLength(body.getBytes().length)
                .setFormId(user.getId())
                .setToId(toId)
                .setCreateTime(System.currentTimeMillis() / 1000)
                .setBody(ByteString.copyFrom(body.getBytes())).build();
        this.send(message, MessageOuterClass.MessageType.PrivateMessage);
    }

    private void send(MessageOuterClass.Message msg, MessageOuterClass.MessageType cmd) throws IOException
    {
        checkConnect();
        MessageOuterClass.MessageRequest request = MessageOuterClass.
                MessageRequest.newBuilder()
                .setType(MessageOuterClass.RequestType.Request)
                .setCmd(cmd)
                .setMessage(msg).build();
        this.outputStream.write(request.toByteArray());
    }

    public boolean isOnLien()
    {
        return this.responseHandler != null && this.responseHandler.isOnline();
    }

    public boolean isConnect()
    {
        return connect;
    }
}
