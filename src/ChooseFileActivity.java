package com.example.rohit.newapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by rohit on 3/13/2016.
 */
public class ChooseFileActivity extends AppCompatActivity {

    private Button selected;
    private Button next;
    ImageView musicIcon;
    TextView music_name;
    String songname=null;
    Peer peer;

    public static ArrayList<String> filePaths=new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_file);

        peer=new Peer();



        String path="/storage/sdcard1/Music";
        final File f=new File(path,"Castle Of Glass.mp3");


        String name=f.getName();
        long size=f.length()/1000000;

        selected=(Button)findViewById(R.id.selected);
        next=(Button)findViewById(R.id.next);
        musicIcon=(ImageView)findViewById(R.id.musicIcon);
        music_name=(TextView)findViewById(R.id.filename);

        music_name.setText(name+"\n"+size+"MB");
        musicIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setText("selected(1)");
               // peer.paths.add(f.getAbsolutePath());
                filePaths.add(f.getAbsolutePath());
                Log.i("file name",filePaths.get(0));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(ChooseFileActivity.this,""+("hi"),Toast.LENGTH_LONG).show();
                                        //

                                       Intent i=new Intent(ChooseFileActivity.this,Send.class);
                                       startActivity(i);

                                    }
                                }
        );


    }


}
