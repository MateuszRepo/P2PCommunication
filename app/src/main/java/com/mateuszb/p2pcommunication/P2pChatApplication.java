package com.mateuszb.p2pcommunication;

import android.app.Application;

/**
 * Created by Mateusz on 10.03.2020.
 */
public class P2pChatApplication extends Application {

    public WifiP2pHandler P2pHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        this.P2pHandler = new WifiP2pHandler(this);
    }

}