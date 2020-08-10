package com.mateuszb.p2pcommunication.connection;

import com.mateuszb.p2pcommunication.pojo.ClientInfo;
import com.mateuszb.p2pcommunication.callbacks.MessageReceivedCallback;
import com.mateuszb.p2pcommunication.data.BaseMessage;
import com.mateuszb.p2pcommunication.data.JoinMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Mateusz on 26.03.2020.
 */
public class ChatClient extends ChatConnection {

    private Thread mThread;
    private java.net.Socket mSocket;
    private InetAddress mTarget;
    private PrintWriter mWriter;
    private ClientInfo mClientInfo;

    public ChatClient(InetAddress address, MessageReceivedCallback callback, ClientInfo info) {
        super(callback);
        this.mClientInfo = info;
        this.mTarget = address;
        this.mThread = new Thread(this.ClientLoop);
        this.mThread.start();
    }

    private Runnable ClientLoop = new Runnable() {
        @Override
        public void run() {
            while (!ChatClient.this.shouldStop) {
                try {
                    if (ChatClient.this.mSocket != null) {
                        ChatClient.this.mSocket.close();
                    }
                    ChatClient.this.mSocket = new Socket();
                    ChatClient.this.isConnected = true;
                    ChatClient.this.mSocket.connect(new InetSocketAddress(ChatClient.this.mTarget, CHAT_PORT), 1000);
                    ChatClient.this.mWriter = new PrintWriter(ChatClient.this.mSocket.getOutputStream());
                    ChatClient.this.mWriter.println(new JoinMessage(ChatClient.this.mClientInfo));
                    ChatClient.this.mWriter.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ChatClient.this.mSocket.getInputStream()));
                    String message = reader.readLine();
                    while (!(ChatClient.this.shouldStop || message == null)) {
                        BaseMessage received = BaseMessage.Deserialize(message);
                        ChatClient.this.Callback.ReceiveMessage(received);
                        message = reader.readLine();
                    }
                    reader.close();
                } catch (IOException ioex) {
                    ChatClient.this.isConnected = false;
                }
            }
        }
    };

    @Override
    public void SendMessage(BaseMessage message) {
        if (this.mWriter != null) {
            this.mWriter.println(message.serialize());
            this.mWriter.flush();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void Stop() {
        super.Stop();
        try {
            if (this.mSocket != null) {
                this.mSocket.close();
            }
            if (this.mThread != null) {
                this.mThread.join();
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (InterruptedException irex) {
            irex.printStackTrace();
            this.mThread.stop();
        }
    }

}