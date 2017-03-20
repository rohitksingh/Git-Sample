package com.example.rohit.newapp1;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by rohit on 3/13/2016.
 */
public class Send  extends AppCompatActivity{

    private EditText ip;
    private Button sendButton;
    private ProgressBar sendBar;
    private Button searchUserButton;

    static FragmentManager manager;
    static FragmentTransaction t;


    static String TARGET_IP="null";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);





        searchUserButton=(Button)findViewById(R.id.searchUserButton);


        searchUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  addSearchFragment();
            }
        });


        final Peer p=new Peer();
        p.paths=ChooseFileActivity.filePaths;

       // Log.i("file name is=",p.paths.get(0));


        ip=(EditText)findViewById(R.id.ipAdd);




        sendButton=(Button)findViewById(R.id.filesendButton);
        sendBar=(ProgressBar)findViewById(R.id.sendbar);
        sendBar.setVisibility(View.GONE);
        sendBar.setMax(10);

        //final SendFile[] send = new SendFile[1];


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendFile(p).execute();
            }

        });



    }

    public void onResume()
    {
        Toast.makeText(Send.this,TARGET_IP,Toast.LENGTH_LONG).show();
        super.onResume();
        if(TARGET_IP!=null)
        ip.setText(TARGET_IP.toString());
    }



    public void addSearchFragment()
    {
        FindAllAvailableUsersFragment frag=new FindAllAvailableUsersFragment();
        manager=getFragmentManager();
        t=manager.beginTransaction();

        t.add(R.id.searchFragment,frag);
        t.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        t.commit();

    }


    private class SendFile extends AsyncTask<Void, Integer, Void> {

        Peer peer;
        SendFileProgressFragment frag;

        public SendFile(Peer peer)
        {
            this.peer=peer;

            frag=new SendFileProgressFragment();
            manager=getFragmentManager();
            t=manager.beginTransaction();
            t.add(R.id.searchFragment,frag);
        }






        public void onPreExecute()
        {
           sendBar.setVisibility(View.VISIBLE);
           sendBar.setProgress(0);
            Log.d("hi","started");
            //peer.ip=ip.getText().toString();

            peer.ip=TARGET_IP;

            Log.d("ip",peer.ip);




            t.commit();


        }


        @Override
        public Void doInBackground(Void... Params)
        {


            publishProgress(1);
            peer.sendProtocol();



            return null;
        }



        public void onProgressUpdate(Integer... values)
        {
            frag.setMessage("file sending...");
        }

        @Override
        public void onPostExecute(Void params)
        {
            frag.setMessage("fileSent");
            Toast.makeText(Send.this,"all file sent",Toast.LENGTH_LONG).show();
            sendBar.setVisibility(View.GONE);

        }




    }



}
