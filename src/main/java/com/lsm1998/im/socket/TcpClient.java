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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Service
@Slf4j
public class TcpClient
{
    @Autowired
    private GlobalConfig globalConfig;

    private ResponseHandler responseHandler;

    private InputStream inputStream;
    private OutputStream outputStream;

    @PostConstruct
    public void init()
    {
        try
        {
            Socket socket = new Socket(globalConfig.getImServerAddress(), globalConfig.getImServerPort());
            this.outputStream = socket.getOutputStream();
            this.inputStream = socket.getInputStream();
            this.responseHandler = new ResponseHandler(this.inputStream);
            this.responseHandler.start();
            log.info("连接完成");
        } catch (IOException e)
        {
            log.error("连接完成失败,err={}", e.getMessage());
        }
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
                .setCmd(MessageOuterClass.MessageType.Handshake)
                .setFormId(user.getId())
                .setBody(ByteString.copyFrom(token.getBytes())).build();
        this.send(message);
    }

    private void send(MessageOuterClass.Message msg) throws IOException
    {
        MessageOuterClass.MessageRequest request = MessageOuterClass.
                MessageRequest.newBuilder()
                .setType(MessageOuterClass.RequestType.Request)
                .setMessage(msg).build();
        this.outputStream.write(request.toByteArray());
    }

    public boolean isOnLien()
    {
        return this.responseHandler != null && this.responseHandler.isOnline();
    }
}
