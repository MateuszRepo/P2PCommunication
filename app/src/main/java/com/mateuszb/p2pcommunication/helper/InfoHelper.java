package com.mateuszb.p2pcommunication.helper;

import android.net.wifi.p2p.WifiP2pInfo;

/**
 * Created by Mateusz on 10.03.2020.
 */
public final  class InfoHelper {

    public static boolean areInfosEquals(WifiP2pInfo info1, WifiP2pInfo info2) {
        if (info1 == null || info2 == null) {
            return false;
        } else {
            return (info1.groupFormed == info2.groupFormed) && (info1.isGroupOwner == info2.isGroupOwner) && (info1.groupOwnerAddress.equals(info2.groupOwnerAddress));
        }
    }

}