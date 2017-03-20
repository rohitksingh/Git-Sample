package com.example.rohit.newapp1;

/**
 * Created by rohit on 3/13/2016.
 */

import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.net.UnknownHostException;
        import java.util.ArrayList;
        import java.util.Scanner;

public class Peer {

    ServerSocket server;
    Socket socket;
    String username;
    DataInputStream dis;
    DataOutputStream dos;

    String yourDir;

    String ip=null;

    Scanner sc=new Scanner(System.in);

    ArrayList<String> paths=new ArrayList<String>();



    public Peer()
    {
       // this.username=username;
       // this.ip=ip;
        yourDir= Environment.getExternalStorageDirectory().getAbsolutePath();


    }

    public static void main(String[] args)
    {
       // Peer p=new Peer(args[0],args[1],args[2]);

       // p.receiveProtocol();

        //p1.sendProtocol();




    }



    public void waitForClient()
    {
        try {
           // System.out.println("Server waiting....");
            Log.i("hi","waiting....");
            server=new ServerSocket(8081);
            socket=server.accept();
            //System.out.println("connected");
            Log.i("hi","connected");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public void initStreams()
    {
        try {
            dis=new DataInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }




    public void search()
    {
        try {


            //System.out.println("searching......");
            Log.i("hi","searching");
            socket=new Socket(ip,8081);
            //System.out.println("connected");
            Log.i("hi","conected");


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public void sendUserName()
    {
        try {
            System.out.println("sending username");
            dos.writeUTF(username);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void receiveUserName()
    {
        try {

            String name=dis.readUTF();
            System.out.println(name+" rec but not print");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(name+" is visible");




        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }








    public void sendProtocol()
    {
        search();
        initStreams();
       // sendUserName();
       // send_init_req();
        fillFiles();
        sendMultipleFiles();
    }



    public void receiveProtocol()
    {
        waitForClient();
        initStreams();
       // receiveUserName();
       // rec_init_ack();
        receiveMultipleFiles();

    }


    public void send_init_req()
    {
        try {

            System.out.println("sending init req...");

            //dis.readByte();

            dos.writeUTF("init send req");
            System.out.println("init req sent");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String responseCode=null;


        try {

            System.out.println("waiting for res");
            responseCode=dis.readUTF();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(responseCode+" resCode for init");


    }


    public void rec_init_ack()
    {

        String reqCode=null;

        System.out.println("waitin from client to sent init req");


        try {

            reqCode=dis.readUTF();

            System.out.println("sjdhsjd"+reqCode);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(reqCode.equals("init send req"))
        {
            System.out.println("Enter y to ok or any key to stop");

            try {
                dos.writeUTF(sc.nextLine());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


    public void sendFile(String filePath)
    {
        File f=new File(filePath);
        FileInputStream fis=null;

        try {
            fis=new FileInputStream(f);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        String fileName=f.getName();

        long fileSize=f.length();

        try {
            dos.writeUTF(fileName);
            dos.writeLong(fileSize);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


		/*
		 * sending body of the file
		 *
		 */


        byte[] buffer=new byte[1024];
        int read=0;

        try {
            while((read=fis.read(buffer))!=-1)
            {
                dos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		/*
		 * waiting for ack from receiver;
		 */


        try {
            dis.readByte();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        System.out.println("file sent successfully");






    }


    public void rec()
    {

       ////////////////// remove this for desktop// String myDir=yourDir+"://mydir";

       /// String myDir="/sdcard/rohitFileTransfer";


        String STORAGE_DIRECTORY=Environment.getExternalStorageDirectory().getAbsolutePath();
        //System.getenv("SECONDARY_STORAGE");

        File folder=new File(STORAGE_DIRECTORY,"RohitFileTransfer");

        folder.mkdirs();


        FileOutputStream fos=null;





        String fileName=null;
        long fileSize=0;

        try {
            fileName=dis.readUTF();
            fileSize=dis.readLong();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        File file=new File(folder,fileName);

        try {
            fos=new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

		/*
		 * receive body of the file
		 */



        int count=0;
        int read=0;

        byte[] buffer=new byte[1024];

        while(true)
        {
            try {
                read=dis.read(buffer);
                fos.write(buffer,0,read);

                count=count+read;
                if(count==fileSize)
                    break;


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        try {
            dos.writeByte(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }









    public void fillFiles()
    {
        /*
        String dir="f://";

        paths.add(dir+"Atom.mp3");
        paths.add(dir+"song.mp3");
        paths.add(dir+"Dont Cry.wma");

       */
 /*
        String s="storage/sdcard1/Music/Castle Of Glass.mp3";
        paths.add(s);


*/


        paths=SelectFileActivity.selectedMultipleFiles;

    }



    public void sendMultipleFiles()
    {
        int n=paths.size();
        Log.d("no of files to be sent",n+" ");

        try {
            dos.writeInt(n);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0;i<n;i++)
        {
            sendFile(paths.get(i));
        }

       // System.out.println(n+" files received");
        Log.i("hi","sent");
    }


    public void receiveMultipleFiles()
    {
        int n=0;

        try {
            n=dis.readInt();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        for(int i=0;i<n;i++)
        {
            rec();
        }

        //System.out.println(n+" files received");
        Log.i("files received",n+" ");

    }




}
