package com.mateuszb.p2pcommunication.data;

import com.mateuszb.p2pcommunication.pojo.ClientInfo;

/**
 * Created by Mateusz on 13.03.2020.
 */
public class JoinMessage extends BaseMessage {

    private ClientInfo mClient;

    public JoinMessage(ClientInfo clientInfo) {
        this.mClient = clientInfo;
    }

    public ClientInfo getmClient() {
        return this.mClient;
    }

    public String serialize() {
        return "JOIN" + this.mClient.serialize();
    }

    public static JoinMessage Deserialize(String serialized) {
        ClientInfo clientInfo = ClientInfo.Deserialize(serialized);
        return new JoinMessage(clientInfo);
    }

}