package com.example.illuminati.fileshare.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Constants.Impl.FileConst;
import com.example.Constants.Impl.ImageConst;
import com.example.Constants.MainConst;
import com.example.Model.Media;
import com.example.Model.impl.Files;
import com.example.Model.impl.Image;
import com.example.Model.impl.Music;
import com.example.Constants.Impl.MusicConst;
import com.example.Constants.MediaConst;
import com.example.illuminati.fileshare.R;
import com.example.illuminati.fileshare.RvAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor=null;

    /*

        TODO MAKE USE OF ABSTRATON BY PUUTING MODELCONST AND MEDIA IN A MAP AND PASSING IT TO SOME METHOD

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      // checkPermission();


        //Media media = new Image();
        MediaConst modelConst = new ImageConst();


        cursor = getContentResolver().query(modelConst.get_EXT_URI(),modelConst.get_Proj(), null,null,null);

        StringBuffer buffer=new StringBuffer();
        String name=null;
        String path=null;
        String type=null;
        String all=null;

        //Toast.makeText(MainActivity.this, modelConst.get_INT_URI()+"\n"+modelConst.get_EXT_URI(), Toast.LENGTH_LONG).show();

        try {
            createMainStorage();

        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"err",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        randomCheck();

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
       // LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
       StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager(2,1);
        rv.setLayoutManager(sgl);
       // Data data =new Data();
       // RvAdapter adapter = new RvAdapter(data.getData());
       // rv.setAdapter(adapter);

        //Images image= new Images();
       // List<Images> imagesList= new ArrayList<Images>();
        List<Media> mediaList = new ArrayList<Media>();
        while (cursor.moveToNext())
        {

            name = cursor.getString(cursor.getColumnIndex(modelConst.get_Proj()[0]));
            path=  cursor.getString(cursor.getColumnIndex(modelConst.get_Proj()[1]));

           // type=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
           // all= "[ {"+name+" } { "+path+" } {"+type+" } ]";
           // buffer.append(all+"\n");
           // Images image= new Images();
           // image.setDisplayName(name);
           // image.setContentType(type);
           // image.setFullPath(path);
            //imagesList.add(image);


            Media media = new Image();
            media.setName(name);
            media.setPath(path);

            mediaList.add(media);
        }

        RvAdapter adapter = new RvAdapter(mediaList);
        rv.setAdapter(adapter);


    }

    public Uri getImages()
    {
        Uri EXT= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        //Uri EXT=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        return EXT;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void createMainStorage() throws IOException {

        String intStorage= Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("rohit", intStorage);
        File file = new File(intStorage+"/IGR");
        if(!file.exists())
        {
            Log.d("rohit","file created");
            //file.mkdir();
            file.mkdirs();
            Log.d("rohit", "file created");
        }
        Toast.makeText(MainActivity.this, file.exists()+"", Toast.LENGTH_LONG).show();


        String path = (new FileConst().get_EXT_URI()).getPath();

        Log.d("rohit","uri= "+new FileConst().get_EXT_URI()+"");
        Log.d("rohit","path ="+path);

    }

    public void randomCheck()
    {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            Log.d("rohit","yes");
            File file= Environment.getExternalStorageDirectory();
            Log.d("rohit",file.getAbsolutePath());
        }
        else{
            Log.d("rohit","no");
        }
    }


    public void checkPermission()
    {
        boolean check =ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        Log.d("rohit","per "+check);

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

             Log.d("rohit","not yet");
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
            Log.d("rohit", "granted");

        }
        // END_INCLUDE(camera_permission_request)
    }
}

