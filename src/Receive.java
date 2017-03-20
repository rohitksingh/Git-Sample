package com.example.rohit.newapp1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rohit on 3/13/2016.
 */


public class Receive extends AppCompatActivity {

    private TextView status;
    private Button receiveFileButton;




    ProgressBar recBar;


    public void onCreate(Bundle savedInstanceStatte)
    {
        super.onCreate(savedInstanceStatte);
        setContentView(R.layout.receive);
        final Peer peer=new Peer();


        status=(TextView)findViewById(R.id.status);
        recBar=(ProgressBar)findViewById(R.id.recbar);
        receiveFileButton=(Button)findViewById(R.id.filereceiveButton);

        receiveFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReceiveFile(peer).execute();
            }
        });

        status.setHint("press receive");

        recBar.setMax(100);

        recBar.setVisibility(View.GONE);






    }


    private class ReceiveFile extends AsyncTask<Void, Integer, Void> {

        Peer peer;
        public ReceiveFile(Peer peer)
        {
            this.peer=peer;
        }


        public void onPreExecute()
        {

            recBar.setVisibility(View.VISIBLE);
            recBar.setProgress(0);
            Log.d("hi", "started");
           // peer.ip=ip.getText().toString();
            Log.d("ip", "rec on pre");
        }


        @Override
        public Void doInBackground(Void... Params)
        {



            sendPessonalInfo();

            peer.receiveProtocol();


            return null;
        }


        @Override
        public void onPostExecute(Void params)
        {
            Toast.makeText(Receive.this, "received", Toast.LENGTH_LONG).show();
            recBar.setVisibility(View.GONE);
        }


    }


    public void sendPessonalInfo()
    {
        Log.d("rohit","sendinf personel info");
        ReceiverInfoFetch r=new ReceiverInfoFetch();
        r.connect();
        r.initStreams();
        r.sendUserName();
        Log.d("rohit", "sendinf personel info end");
    }



}
