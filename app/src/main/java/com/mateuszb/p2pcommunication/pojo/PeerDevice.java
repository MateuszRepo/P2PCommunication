package com.mateuszb.p2pcommunication.pojo;

import android.net.wifi.p2p.WifiP2pDevice;

/**
 * Created by Mateusz on 12.03.2020.
 */
public class PeerDevice {

    private boolean isAlreadyInConversation;
    private WifiP2pDevice device;

    public PeerDevice(WifiP2pDevice device, boolean alreadyInConversation) {
        this.device = device;
        this.isAlreadyInConversation = alreadyInConversation;
    }

    public WifiP2pDevice getDevice() {
        return this.device;
    }

    public boolean isAlreadyInConversation() {
        return this.isAlreadyInConversation;
    }
}