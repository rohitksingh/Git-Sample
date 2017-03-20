package com.example.illuminati.fileshare.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.illuminati.fileshare.R;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Created by Illuminati on 3/18/2017.
 */
public class TurnOnOff extends AppCompatActivity {

    private Button b1;
    private Button b2;
    int permissionCheck=0;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network);

        b1=(Button)findViewById(R.id.networkButton);
        setText();

        b2 =(Button)findViewById(R.id.connect);

        b2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),com.example.illuminati.fileshare.Activities.Connect.class);
                startActivity(intent);
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configApState(getApplicationContext());
                boolean permission =checkSystemWritePermission();
                if(!permission)
                {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                    startActivity(intent);
                }
                setText();
            }
        });
    }


    public static boolean isApOn(Context context) {

        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);

        Log.d("Cong", getConfig().SSID);



        try {
            Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            Log.d("ROHIT", (Boolean) method.invoke(wifimanager) + " h");
            return (Boolean) method.invoke(wifimanager);

        }
        catch (Throwable ignored) {}
        Log.d("ROHIT", "false ");
        return false;
    }

    // toggle wifi hotspot on or off
    public static boolean configApState(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiConfiguration wificonfiguration = getConfig();
        try {
            // if WiFi is on, turn it off
            if(isApOn(context)) {
                wifimanager.setWifiEnabled(false);
             }
            Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifimanager, wificonfiguration, !isApOn(context));
            return true;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setText()
    {
        boolean result=isApOn(getApplicationContext());
        b1.setText(result+" "+permissionCheck);
    }


    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);


            if(retVal){
                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();

            }
        }
        return retVal;
    }

    // TO CUSTOMIZE NAME AND PASSKEY OF NETWORK;
    public static WifiConfiguration getConfig()
    {
       String name ="meranaamchuchuchu";
       String pass ="123456789";

      // WifiConfiguration conf = new WifiConfiguration();
        //conf.SSID ="\"name\"";

      //  conf.preSharedKey = "\"pass\"" ;
       // conf.preSharedKey = "\""+pass+"\"";

       // WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
       // WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = "lolu";
        wc.preSharedKey  = "password";
        wc.hiddenSSID = true;
        wc.status = WifiConfiguration.Status.ENABLED;
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        return wc;
    }



}
