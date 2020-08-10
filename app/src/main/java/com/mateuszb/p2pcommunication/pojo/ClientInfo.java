package com.mateuszb.p2pcommunication.pojo;

/**
 * Created by Mateusz on 10.03.2020.
 */
public class ClientInfo {

    private String mDeviceAddress;
    private String mUsername;

    public ClientInfo(String deviceAddress, String username) {
        this.mDeviceAddress = deviceAddress;
        this.mUsername = username;
    }

    public static ClientInfo GetComparable(String deviceAddress) {
        return new ClientInfo(deviceAddress, "");
    }

    public String serialize() {
        return this.mDeviceAddress + "#" + this.mUsername;
    }

    public static ClientInfo Deserialize(String serialized) {
        int index = serialized.indexOf("#");
        String deviceAddress = serialized.substring(0, index - 1);
        String username = serialized.substring(index + 1);
        return new ClientInfo(deviceAddress, username);
    }

    public String getmDeviceAddress() {
        return this.mDeviceAddress;
    }

    public String getmUsername() {
        return this.mUsername;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ClientInfo) {
            ClientInfo client = (ClientInfo) object;
            return (client.mDeviceAddress == this.mDeviceAddress);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        long decimal = Long.parseLong(this.mDeviceAddress.replace(":", ""));
        return Long.valueOf(decimal).hashCode();
    }
}