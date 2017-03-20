package com.example.rohit.newapp1;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rohit on 4/22/2016.
 */
public class ConnectForTheFirstTime {

    String connectTo;
    Socket socket;
    DataInputStream dis;

    String END_USERNAME;

    public ConnectForTheFirstTime(String connectTo)
    {
        this.connectTo=connectTo;
        Log.d("rohit",connectTo);
    }


    public void connect()
    {
        try {
            socket=new Socket(connectTo,8080);
            //System.out.println("connected  to "+connectTo);
            Log.d("rohit","connected  to "+connectTo);
        }
        catch(ConnectException e)
        {
            System.out.println("dssdkjsd");
        }
        catch(UnknownHostException e)
        {
            // System.out.println("connection falied to "+connectTo);
            Log.d("rohit","connection falied to "+connectTo);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        }
    }

    public void initStreams()
    {



        try {
            dis=new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void getInfo()
    {
        try {
            END_USERNAME=dis.readUTF();
           // System.out.println("name===== "+name);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                dis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
