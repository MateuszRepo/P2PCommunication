package com.mateuszb.p2pcommunication.callbacks;

import com.mateuszb.p2pcommunication.data.BaseMessage;

/**
 * Created by Mateusz on 15.03.2020.
 */
public interface MessageReceivedCallback {

    void ReceiveMessage(BaseMessage msg);

}