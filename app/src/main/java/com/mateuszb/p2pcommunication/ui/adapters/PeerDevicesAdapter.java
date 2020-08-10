package com.mateuszb.p2pcommunication.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mateuszb.p2pcommunication.pojo.PeerDevice;
import com.mateuszb.p2pcommunication.R;

import java.util.List;

/**
 * Created by Mateusz on 12.03.2020.
 */
public class PeerDevicesAdapter extends BaseAdapter {

    private android.content.Context Context;
    private List<PeerDevice> PeerDevices;

    public PeerDevicesAdapter(Context context, List<PeerDevice> devices) {
        this.Context = context;
        this.PeerDevices = devices;
    }

    public void updatePeerDevices(List<PeerDevice> devices) {
        this.PeerDevices = devices;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.PeerDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return this.PeerDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate view
        View itemView = inflater.inflate(R.layout.peerdevice_item, parent, false);
        TextView txtDeviceAddress = (TextView) itemView.findViewById(R.id.txtDeviceName);
        // get item
        PeerDevice device = this.PeerDevices.get(position);
        // set properties
        txtDeviceAddress.setText(device.getDevice().deviceName);
        itemView.setBackgroundColor(device.isAlreadyInConversation() ? Color.GREEN : Color.WHITE);
        return itemView;
    }
}
