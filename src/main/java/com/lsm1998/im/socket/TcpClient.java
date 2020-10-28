package com.lsm1998.im.socket;

import com.google.protobuf.ByteString;
import com.lsm1998.im.domain.User;
import com.lsm1998.im.listener.ContextAwareUtil;
import message.MessageOuterClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Service
public class TcpClient
{
    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private Integer port;

    private InputStream inputStream;
    private OutputStream outputStream;

    @PostConstruct
    public void init()
    {
        try
        {
            Socket socket = new Socket(address, port);
            this.outputStream = socket.getOutputStream();
            this.inputStream = socket.getInputStream();
            new ResponseHandler(this.inputStream).start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("连接完成");
    }

    /**
     * 发送握手信息
     *
     * @param token
     * @throws IOException
     */
    public void sendHandshake(String token) throws IOException
    {
        User user = ContextAwareUtil.getUser();
        ByteString body = ByteString.EMPTY;
        ByteString.newOutput().write(token.getBytes());
        this.send(MessageOuterClass.Message.newBuilder()
                .setLength(token.getBytes().length)
                .setCmd(MessageOuterClass.RequestType.Handshake)
                .setFormId(user.getId())
                .setBody(body).build());
    }

    private void send(MessageOuterClass.Message msg) throws IOException
    {
        MessageOuterClass.MessageRequest request = MessageOuterClass.
                MessageRequest.newBuilder()
                .setMessage(msg).build();
        this.outputStream.write(request.toByteArray());
    }
}
