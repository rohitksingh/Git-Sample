package com.example.rohit.newapp1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by rohit on 4/22/2016.
 */
public class ReceiverInfoFetch {

    public ServerSocket server;
    Socket socket;
    DataOutputStream dos;

    public void connect()
    {
        try {
            System.out.println("waiting for initial info connecton");
            server=new ServerSocket(8080);
            System.out.println("initial connected");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            System.out.println("waiting");
            socket=server.accept();
            System.out.println("connected to server");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void initStreams()
    {
       // DataInputStream dis;


        try {
            dos=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void sendUserName()
    {
        PersonalInfo info=new PersonalInfo();
        try {
           // dos.writeUTF(info.getName());

            dos.writeUTF(PersonalInfo.THIS_IS_MY_NAME);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
