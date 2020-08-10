package com.mateuszb.p2pcommunication.data;

import com.mateuszb.p2pcommunication.pojo.ClientInfo;

/**
 * Created by Mateusz on 13.03.2020.
 */
public class LeaveMessage extends BaseMessage {

    private ClientInfo mClient;

    public LeaveMessage(ClientInfo clientInfo) {
        this.mClient = clientInfo;
    }

    public String serialize() {
        return "LEAVE" + this.mClient.serialize();
    }

    public static LeaveMessage Deserialize(String part) {
        ClientInfo clientInfo = ClientInfo.Deserialize(part);
        return new LeaveMessage(clientInfo);
    }

    public ClientInfo getmClient() {
        return this.mClient;
    }

}
