package com.mateuszb.p2pcommunication.data;

import com.mateuszb.p2pcommunication.pojo.ClientInfo;

/**
 * Created by Mateusz on 13.03.2020.
 */
public class ChatMessage extends BaseMessage {

    private String mContent;
    private ClientInfo mClient;

    public ChatMessage(ClientInfo client, String content) {
        this.mContent = content;
        this.mClient = client;
    }

    public String serialize() {
        return "CHAT" + this.mClient.serialize() + "#" + this.mContent;
    }

    public static ChatMessage Deserialize(String part) {
        int index = part.lastIndexOf("#");
        String clientString = part.substring(0, index - 1);
        ClientInfo client = ClientInfo.Deserialize(clientString);
        String content = part.substring(index + 1);
        return new ChatMessage(client, content);
    }

    public ClientInfo getmClient() {
        return this.mClient;
    }

    public String getmContent() {
        return this.mContent;
    }

}
