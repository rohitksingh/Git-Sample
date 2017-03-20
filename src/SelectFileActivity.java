package com.example.rohit.newapp1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by rohit on 4/21/2016.
 */
public class SelectFileActivity extends AppCompatActivity {

    ListView musicList;
    Button bucket;
    Button next;

    int count=0;

    String FILE_DIR;

    //ArrayList<String> musicFiles;

    LinkedHashMap<Integer,String> hashmap;

    File f;


    static ArrayList<String> selectedMultipleFiles=new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupWidowAnimations();
        setContentView(R.layout.selectfiles);


        musicList=(ListView)findViewById(R.id.musicFiles);




        FILE_DIR=System.getenv("SECONDARY_STORAGE");
        FILE_DIR=FILE_DIR+"/Music";
        //musicFiles=new ArrayList<String>();

        hashmap=new LinkedHashMap<Integer,String>();



        String extStorage=System.getenv("SECONDARY_STORAGE");
        String intStorage= Environment.getExternalStorageDirectory().getAbsolutePath();
        Toast.makeText(SelectFileActivity.this,intStorage,Toast.LENGTH_LONG).show();


        // f=new File("storage/sdcard1/Music");

        f=new File(extStorage+"/Music");
        String[] s=f.list();
        myAdapter adapter=new myAdapter(getApplicationContext(),s);
        musicList.setAdapter(adapter);


        bucket=(Button)findViewById(R.id.bucketButton);
        next=(Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i=new Intent(SelectFileActivity.this,Send.class);
                                        startActivity(i);
                                    }
                                }
        );


    }


    public ArrayList<String> getMedia()
    {
        ArrayList<String> al=new ArrayList<String>();


        // MEDIA URI =MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

       // final Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final Uri inUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE
        };
        Cursor c=getContentResolver().query(inUri,cursor_cols,null,null,null);

        while(c.moveToNext()) {
            String name1 = c.getString(c.getColumnIndex(MediaStore.Audio.Media._ID));
            String name2 = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));

            al.add(name1 + " " + name2);
        }
        return al;
    }


    class myAdapter extends ArrayAdapter<String>
    {
        Context context;


        String colors[]=new String[]{"#2196F3","#009688","#FF9800","#FFEB3B","#9C27B0","#FF5722"};
        String[] s=new String[]{"rohit","singh","abs"};
        myAdapter(Context c,String[] s)
        {
            super(c,R.layout.music_list_row,R.id.musicFilerowId,s);
            this.s=s;
            context=c;
            

        }



        public View getView(final int position,View convertView,ViewGroup parent)
        {



            LayoutInflater inflater=(LayoutInflater)context.getSystemService(ContextThemeWrapper.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.music_list_row,parent,false);

            int index=(position)%5;
            row.setBackgroundColor(Color.parseColor(colors[index]));
            TextView fileName=(TextView)row.findViewById(R.id.musicFilerowId);
            TextView fileSize=(TextView)row.findViewById(R.id.musicFileSize);

            final CheckBox checkBox=(CheckBox)row.findViewById(R.id.selectCheckBox);
            fileName.setText(s[position].toString());

            File f1=new File(f,s[position]);

            fileSize.setText(f1.length()+"bytes");

            checkBox.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                if (checkBox.isChecked()) {
                                                    Toast.makeText(SelectFileActivity.this, "added", Toast.LENGTH_SHORT).show();
                                                    selectedMultipleFiles.add(FILE_DIR+"/"+s[position]);


                                                } else {
                                                    Toast.makeText(SelectFileActivity.this, "removed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
            );


            return row;



        }
    }


    private void setupWidowAnimations()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Fade fade=new Fade();
            fade.setDuration(1000);

            Slide slide=new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);

            getWindow().setReturnTransition(slide);
        }
    }

}
