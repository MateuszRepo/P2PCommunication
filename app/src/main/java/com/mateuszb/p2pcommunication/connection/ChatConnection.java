package com.mateuszb.p2pcommunication.connection;

import com.mateuszb.p2pcommunication.callbacks.MessageReceivedCallback;
import com.mateuszb.p2pcommunication.data.BaseMessage;

/**
 * Created by Mateusz on 25.03.2020.
 */
public abstract class ChatConnection {

    public static final int CHAT_PORT = 8050;

    public boolean isConnected = false;

    protected boolean shouldStop = false;

    protected MessageReceivedCallback Callback;

    protected ChatConnection(MessageReceivedCallback callback) {
        this.Callback = callback;
    }

    public abstract void SendMessage(BaseMessage message);

    public void Stop() {
        this.isConnected = false;
        this.shouldStop = true;
    }

}
