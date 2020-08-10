package com.mateuszb.p2pcommunication.data;

/**
 * Created by Mateusz on 12.03.2020.
 */
public abstract class BaseMessage {

    public abstract String serialize();

    public static BaseMessage Deserialize(String serialized) {
        if (serialized.startsWith("JOIN")) {
            return JoinMessage.Deserialize(serialized.substring(4));
        } else if (serialized.startsWith("LEAVE")) {
            return LeaveMessage.Deserialize(serialized.substring(5));
        } else if (serialized.startsWith("CHAT")) {
            return ChatMessage.Deserialize(serialized.substring(4));
        } else {
            throw new IllegalArgumentException("Not deserializable");
        }
    }

}