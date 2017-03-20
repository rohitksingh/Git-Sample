package com.example.illuminati.fileshare.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.illuminati.fileshare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Illuminati on 3/18/2017.
 */
public class Connect extends AppCompatActivity {

    public WifiManager mWifiManager;
    private ListView scanList;

    public void onCreate(Bundle savediNstanceState)
    {
        super.onCreate(savediNstanceState);
        setContentView(R.layout.wifiavail);
        scanList=(ListView)findViewById(R.id.musicListView);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration configuration = new WifiConfiguration();

        mWifiManager.addNetwork(configuration);
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mWifiManager.startScan();



    }


    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                List<ScanResult> mScanResults = mWifiManager.getScanResults();
                // add your logic her

                // ArrayList<ScanResult> name=new ArrayList<ScanResult>();
                ArrayList<String> sr=new ArrayList<String>();
                String name;
                for(int i=0;i<mScanResults.size();i++)
                {
                    name = mScanResults.get(i).SSID;
                    if(name.startsWith("J"))
                        sr.add(mScanResults.get(i).SSID);
                }
                MY adapter=new MY(getApplicationContext(),sr);
                scanList.setAdapter(adapter);
                //  ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,sr);
                //   scanList.setAdapter(adapter);
            }
        }
    };
}




class MY extends ArrayAdapter<String>
{

    Context context;
    List<String> list;

    public MY(Context c,List<String> list)
    {
        super(c, 0, list);
        context=c;
        this.list=list;
    }


    public View getView(int position ,View convertView,ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.wifi_single_row,parent,false);
        //ImageView img=(ImageView)row.findViewById(R.id.songBitMap);

        TextView songSize=(TextView)row.findViewById(R.id.name);

        //img.setImageResource(R.drawable.wifi);
        songSize.setText(list.get(position));



        return row;
    }
}